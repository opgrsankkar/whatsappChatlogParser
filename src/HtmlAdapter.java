import java.util.ArrayList;

public class HtmlAdapter {
    public static String encloseInSpan(String text){
        text = "<span >"+text+"</span>";
        return text;
    }
    public static String encloseInDiv(String text){
        text = "<div >"+text+"</div>";
        return text;
    }

    public static String encloseInBody(String text){
        text = "<body >"+text+"<body>";
        return text;
    }

    public static String addAttribute(String tagFull, String attribute, String value){
        tagFull = getStartTag(tagFull).substring(0,tagFull.indexOf(">"))
                +" " + attribute
                +"=\""+ value +"\">"
                +getText(tagFull)
                +getEndTag(tagFull);
        return tagFull;
    }

    public static String addStyle(String tagFull, String key, String value){
        if(getStartTag(tagFull).indexOf("style=") == -1){
            tagFull = addAttribute(tagFull,"style","");
        }
        int styleStart = getStartTag(tagFull).indexOf("style=\"")+7;
        int styleEnd = getStartTag(tagFull).indexOf("style=\"",styleStart);
        tagFull = tagFull.substring(0,styleStart) +
                key + ":" +
                value + ";" +
                tagFull.substring(styleStart);
        return tagFull;
    }

    private static String getText(String tagFull){
        int endOfStartTag = tagFull.indexOf(">");
        int startOfEndTag = tagFull.lastIndexOf("<");
        return tagFull.substring(endOfStartTag+1,startOfEndTag);

    }

    private static String getStartTag(String tagFull){
        int endOfStartTag = tagFull.indexOf(">");
        return tagFull.substring(0,endOfStartTag+1);
    }
    private static String getEndTag(String tagFull){
        int endOfStartTag = tagFull.indexOf(" ");
        return "</"+tagFull.substring(1,endOfStartTag)+">";
    }

    public static String encloseInChatDiv(Exchange currentExchange, Exchange previousExchange, ArrayList<String> exchangersList) {
        String exchangerDiv = "";
        String messageDiv = "";
        String timestampSpan = "";
        String finalString = "";
        if(currentExchange.getExchanger().equals(exchangersList.get(0))){
            exchangerDiv = encloseInDiv(currentExchange.getExchanger());
            exchangerDiv = addStyle(exchangerDiv,"font-weight","bold");
            messageDiv = encloseInDiv(currentExchange.getMessage());

            finalString = exchangerDiv + messageDiv;
            finalString = encloseInSpan(finalString);
            finalString = addStyle(finalString,"border-radius","5px");
            finalString = addStyle(finalString,"background-color","powderblue");
            finalString = encloseInDiv(finalString);
            finalString = addStyle(finalString,"text-align","left");
        }
        if(currentExchange.getExchanger().equals(exchangersList.get(1))){
            finalString = encloseInSpan(currentExchange.getFullText());
            finalString = addStyle(finalString,"border-radius","5px");
            finalString = addStyle(finalString,"background-color","lightgrey");
            finalString = encloseInDiv(finalString);
            finalString = addStyle(finalString,"text-align","right");
        }

        return finalString;
    }
    public static void main(String[] args){
        String test = "hello";
        test = encloseInDiv(test);
        test = addStyle(test,"text-align","left");
        test = addStyle(test,"background-color","powderblue");
        System.out.println(test);
    }
}
