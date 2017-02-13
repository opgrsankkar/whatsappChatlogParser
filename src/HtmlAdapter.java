import java.util.ArrayList;

public class HtmlAdapter {
    public static String encloseInSpan(String text){
        text = "<span >"+text+"</span>";
        return text;
    }

    public static String encloseInP(String text) {
        text = "<p >" + text + "</p>";
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
        String exchangerDiv = encloseInDiv(currentExchange.getExchanger());
        String messageDiv = currentExchange.getMessage();
        String timestampSpan = encloseInDiv(currentExchange.getTimestamp());
        String finalString;

        exchangerDiv = addStyle(exchangerDiv, "font-weight", "bold");
        if (previousExchange != null && currentExchange.getExchanger().equals(previousExchange.getExchanger())) {
            finalString = messageDiv;
        } else {
            finalString = exchangerDiv + "\n" + messageDiv;
        }
        finalString = encloseInDiv(finalString);
        finalString = addStyle(finalString, "max-width", "60%");
        finalString = addStyle(finalString, "padding", "5px");
        finalString = addStyle(finalString, "border-radius", "5px");
        if (previousExchange != null && currentExchange.getExchanger().equals(previousExchange.getExchanger())) {
            finalString = addStyle(finalString, "margin-top", "2px");
        } else {
            finalString = addStyle(finalString, "margin-top", "10px");
        }

        if (currentExchange.getExchanger().equals(exchangersList.get(0))) {
            finalString = addStyle(finalString, "box-shadow", "2px 2px 4px 0.5px rgba(0,0,0,0.2)");
            finalString = addStyle(finalString, "background-color", "beige");
            finalString = addStyle(finalString,"text-align","left");
        }
        if(currentExchange.getExchanger().equals(exchangersList.get(1))){
            finalString = addStyle(finalString, "box-shadow", "-2px 2px 4px 0.5px rgba(0,0,0,0.2)");
            finalString = addStyle(finalString, "background-color", "azure");
            finalString = addStyle(finalString,"text-align","right");
            finalString = addStyle(finalString, "margin-left", "40%");
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
