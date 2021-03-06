import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exchange {
    private String fullText = "";
    private String exchanger = "";
    private String timestamp = "";
    private String message = "";

    protected Exchange(String text) throws ExchangeHasNoTimeStampException {
        fullText = text;
        //pattern to find strings starting with timestamp
        String pattern = "[0-3][0-9]/[0-1][0-9]/[0-9]{4} [0-2][0-9]:[0-5][0-9]:[0-5][0-9]:";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(fullText);
        if(m.find()){
            timestamp = fullText.substring(0,19);

            String exchangeWithoutTimestamp = fullText.substring(21);

            exchanger = exchangeWithoutTimestamp.substring(0,exchangeWithoutTimestamp.indexOf(':'));

            message = exchangeWithoutTimestamp.substring(exchangeWithoutTimestamp.indexOf(':')+1);
        }
        else{
            throw new ExchangeHasNoTimeStampException();
        }
    }

    protected String getExchanger() {
        return exchanger;
    }

    protected String getTimestamp() {
        return timestamp;
    }

    protected String getMessage() {
        return message;
    }

    protected String getFullText() {
        return fullText;
    }

    protected void addToMessage(String extraText) {
        message += "<br />" + extraText;
        fullText += "<br />" + extraText;
    }

    protected class ExchangeHasNoTimeStampException extends Exception {

    }
}
