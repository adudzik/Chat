import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by Arek
 * <p>
 * This class answers for users questions.
 */
class BotBoySystem {

    private String answer = "";

    String answer(String message) {
        this.answerForQuestion(message);
        return this.answer;
    }

    private void answerForQuestion(String message) {
        Pattern pattern = Pattern.compile("(godzina|czas|time)+");

        if (pattern.matcher(message).find()) {
            this.answer = getDate();
            return;
        }

        pattern = Pattern.compile("(dzień|day)+");

        if (pattern.matcher(message).find()) {
            this.answer = getDay();
            return;
        }

        pattern = Pattern.compile("(pogoda|weather)+");
        Random rand = new Random();
        if (pattern.matcher(message).find()) {
            String weather[] = {"jest słonecznie i ciepło.", "jest zimno i pada śnieg.", "leje jak z cebra.", "jest pochmurno, zanosi się na deszcz."};
            this.answer = "Dzisiaj w Krakowie " + weather[rand.nextInt(4)];
            return;
        }

        String answers[] = {"Pisz po polsku, bo nie mogę Cię zrozumieć!", "Nie wiem czego chcesz.", "Zaprogramowano mnie bym odpowiadał tylko na właściwe pytania.",
                "Nie potrafię Cię zrozumieć.", "Nie potrafię Ci pomóc", "Nie pomogę Ci, bo tak mi się podoba."};

        this.answer = answers[rand.nextInt(6)];
    }

    private String getDate() {
        return "Dzisiaj jest " + new SimpleDateFormat("hh:mm:ss").format(new Date()) + ". Czy przygotowałeś się już do sesji?";
    }

    private String getDay() {
        String[] days = {"Poniedziałek", "Wtorek", "Sroda", "Czwartek", "Piątek", "Sobota", "Niedziela"};
        int index = Integer.parseInt(new SimpleDateFormat("u").format(new Date())) - 1;
        String dayOfTheWeek = days[index];
        String optionalMessage = "";
        switch (index) {
            case 0:
                optionalMessage = "Znowu...";
                break;

            case 1:
                optionalMessage = "Dziś wykład z PO!";
                break;

            case 2:
                optionalMessage = "Coraz bliżej końca!";
                break;

            case 3:
                optionalMessage = "Jeszcze jedne dzień i piątek!";
                break;

            case 4:
                optionalMessage = "Tak! Wreszcie!";
                break;

            case 5:
                optionalMessage = "Weekendzik!";
                break;

            case 6:
                optionalMessage = "Niee... Jutro poniedziałek :(";
                break;
        }
        return "Dziś " + dayOfTheWeek + ". " + optionalMessage;
    }
}
