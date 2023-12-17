package com.dsheldon.jalinbisa.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class Tools {
//    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    /**
     * For Print StackTrace Exception
     * @param exception {@link Exception}
     * @return String
     */
    public String printStackTrace(Exception exception){
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] st = exception.getStackTrace();
        sb.append(exception.getClass().getName() + ": " + exception.getMessage() + "\n");
        for (int i=0; i < st.length; i++)
        {
            sb.append("\t at " + st[i].toString() + "\n");
        }
        return sb.toString();
    }

    /**
     * For Getting PID Service Process
     * @return Long
     */
    public Long getPid(){
        RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();
        String jvm = mxBean.getName();
        long pid = Long.valueOf(jvm.split("@")[0]);
        return pid;
    }

    /**
     * For Load Property Logger log4j
     */
    public void loadLogger(){
        String pathLog = System.getProperty("user.dir") + "/log4j.properties";
//        PropertyConfigurator.configure(pathLog);
    }


    /**
     * For Getting DateTime in GMT-7 MMddHHmmss
     * @return String
     */
    public String getGMT(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
        Date date = new Date(System.currentTimeMillis() - 3600 * 7000);
        return sdf.format(date);
    }

    /**
     * For Getting LocalTime HHmmss
     * @return String
     */
    public String getLocalTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * For Getting LocalDate MMdd
     * @return String
     */
    public String getLocalDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * For Getting SettleDate MMdd
     * @return String
     */
    public String getSettleDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * For Auto Generate Random TraceNum 6 Digits
     * @return String
     */
    public String getStan(){
        Random rnd = new Random();
        return String.format("%06d", rnd.nextInt(999999));
    }

    /**
     * For Auto Generate Random RefNum 12 Digits
     * @return String
     */
    public String getRRN(){
        Random rnd = new Random();
        int rrn = rnd.nextInt(899999) + 100000;
        return String.format("%-12s", rrn);
    }

    public String getYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    public String getHHmm(){
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        Date date = new Date();
        return sdf.format(date);
    }

    public String getDdMmYyyy(){
//        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        return sdf.format(date);
    }

    public String getDdMmYy(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        Date date = new Date();
        return sdf.format(date);
    }

    public String getHhMmSs(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }

    public String getYyMmDd(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        return sdf.format(date);
    }

    public String GetDateTimeAt(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return format.format(date);
    }


    public String getInst(String inst){
        String result = "";
        switch (inst){
            case "RNTSB24D":
                result = "RINTIS SEJAHTERA";
                break;
            case "UPI":
                result = "UNION PAY";
                break;
            case "JALIN":
                result = "JALIN";
                break;
            default:
                result = "Unrecognized";
        }
        return result;
    }

    public String maskPAN(String data){
        data = data.trim();
        int START = 6;
        int END = 4;
        int maskLen = data.length() - (START + END);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < maskLen; i++){
            sb.append("*");
        }

        String maskCard = data.substring(0, START) + sb +
                data.substring(data.length() - END, data.length());

        return maskCard;
    }

    public String removeASCII(String data){
        if(!isAscii(data)) {
            // strips off all non-ASCII characters
            data = data.replaceAll("[^\\x00-\\x7F]", "");

            // erases all the ASCII control characters
            data = data.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");

            // removes non-printable characters from Unicode
            data = data.replaceAll("\\p{C}", "").trim();
        }
        return data;
    }

    private boolean isAscii(String v) {
        if (v != null && !v.isEmpty()) {
            for(char c : v.toCharArray()) {
                if (c < 0x20 || c > 0x7E) return false;
            }
        }
        return true;
    }

    public <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


}
