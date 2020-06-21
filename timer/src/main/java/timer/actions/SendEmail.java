package timer.actions;

import timer.base.TimerApp;
import timer.report.ReportSender;
import java.lang.Object;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class SendEmail extends Actions {

    public SendEmail() {
        this.action = this.getClass().getSimpleName().toLowerCase();
    }

    @Override
    protected void perform(String[] input, TimerApp app) {
        sendEmail(app, input);
    }

    private void sendEmail(TimerApp app, String[] input) {

        if (input.length == 1) {
            ReportSender.send("extremetimerPE2020@wp.pl", "Test3", "This is the report", app.createReport(null, null, "report"));
        } else if (input.length >= 4) {
            if (input.length == 4 && !ifContains(input,"\"")){
                ReportSender.send(input[1], input[2], input[3], app.createReport(null, null, "report"));
            } else {
                String subjectAndText = "";
                String[] newInput = new String[4];
                for (int s=2; s<input.length; s++)
                {
                    subjectAndText= subjectAndText+input[s] + " ";
                }
                String[] result = subjectAndText.split("\"");
                result = Arrays.stream(result).filter(x -> !StringUtils.isBlank(x)).toArray(String[]::new);
                try{
                    newInput[0]=input[0];
                    newInput[1]=input[1];
                    newInput[2]=result[0];
                    newInput[3]=result[1];
                    ReportSender.send(newInput[1], newInput[2], newInput[3], app.createReport(null, null, "report"));
                } catch (Exception e){
                    System.out.println("Wrong parameters provided");
                }
            }
        } else {
            System.out.println("Wrong parameters provided");
        }

    }

    protected static boolean ifContains(String[] accept, String c) {
        for (int i = accept.length - 1; i >= 0; i--) {
            if (accept[i].contains(c)) {
                return true;
            }
        }
        return false;
    }
}
