import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Text {

    //list of exchanges
    static ArrayList<Exchange> exchangesList = new ArrayList<Exchange>();
    //list of exchangers
    static ArrayList<String> exchangersList = new ArrayList<String>();

    //Constructor to extract exchangers exchanges 'with and without timestamp'
    public Text(String fileName) throws IOException {
        String sCurrentLine = null;
        FileReader mFileReader = new FileReader(fileName);
        BufferedReader mBufferedReader = new BufferedReader(mFileReader);
        sCurrentLine = mBufferedReader.readLine();
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

        ListIterator exchangeIterator = mText.exchangesList.listIterator();
        while(exchangeIterator.hasNext()){
            Exchange currentExchange = (Exchange) exchangeIterator.next();
            Exchange previousExchange = (Exchange) exchangeIterator.previous();
            exchangeIterator.next();
            String currentDiv = HtmlAdapter.encloseInChatDiv(currentExchange,previousExchange,mText.exchangersList);
            html += currentDiv + "\n";
        }

        html = HtmlAdapter.encloseInDiv(html);
        html = HtmlAdapter.addStyle(html,"width","50%");
        html = HtmlAdapter.addStyle(html,"margin","0 auto");
        html = HtmlAdapter.encloseInBody(html);
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
