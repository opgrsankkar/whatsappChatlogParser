import java.io.*;
import java.util.ArrayList;

public class Text {

    //list of exchanges
    private static ArrayList<Exchange> exchangesList = new ArrayList<Exchange>();
    //list of exchangers
    private static ArrayList<String> exchangersList = new ArrayList<String>();

    //Constructor to extract exchangers exchanges 'with and without timestamp'
    public Text(String fileName) throws IOException {
        FileReader mFileReader = new FileReader(fileName);
        BufferedReader mBufferedReader = new BufferedReader(mFileReader);
        String sCurrentLine = mBufferedReader.readLine();
        //read each line into a new element in exchangesListFull
        while((sCurrentLine = mBufferedReader.readLine())!=null){
            try {
                exchangesList.add(new Exchange(sCurrentLine));
            } catch (Exchange.ExchangeHasNoTimeStampException e) {
                exchangesList.get(exchangesList.size()-1).addToMessage(sCurrentLine);
            }
        }

        //add all unique exchangers in exchangersList
        for(Exchange currentExchange: exchangesList){
            if(!exchangersList.contains(currentExchange.getExchanger())){
                exchangersList.add(currentExchange.getExchanger());
            }
        }
    }
    public static void main(String[] args) throws IOException{
        Text mText = new Text("tst.txt");
        String html = "";

        for (int i = 0; i < exchangesList.size(); i++) {
            Exchange currentExchange = exchangesList.get(i);
            Exchange previousExchange = null;
            if (i > 0) {
                previousExchange = exchangesList.get(i - 1);
            }
            String currentDiv = HtmlAdapter.encloseInChatDiv(currentExchange, previousExchange, exchangersList);
            html += currentDiv + "\n";
        }

        html = HtmlAdapter.encloseInDiv(html);
        html = HtmlAdapter.addStyle(html, "margin", "0 auto auto 20%");
        html = HtmlAdapter.encloseInBody(html);
        html = HtmlAdapter.addAttribute(html, "bgcolor", "powderblue");
        try {
            FileWriter fileWriter = new FileWriter(new File("index.html"));
            fileWriter.write(html);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
