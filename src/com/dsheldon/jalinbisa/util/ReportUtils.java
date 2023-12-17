package com.dsheldon.jalinbisa.util;

import com.dsheldon.jalinbisa.constant.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ReportUtils {
    private static Tools tools = new Tools();
    private static IniUtils ini = new IniUtils(Constants.CONFIG_FILE, Constants.CONFIG_DIR);
    private static final String OUTPUT_DIR = System.getProperty("user.dir") + "/output";

    public static void generateReport(String role) {
        // Get parameters from .ini config file
        String[] institutionList = ini.getVal("BANKROLE", role).split(",");
        String institutionReportName = ini.getVal("TEMPLATE", "REPORT_NAME_" + role);
        String product = ini.getVal("TEMPLATE", "PRODUCT");


        for (String institution : institutionList) {
            System.out.println("Institution:" + institution);

            // Retrieve bank info
            String[] bankInfo = ini.getVal("BANKINFO", institution).split(";");
            String bankCode = bankInfo[0];
            String bankName = bankInfo[1];

            // Prepare directory path
            String directoryPath = OUTPUT_DIR.concat("/").concat(institution);
            // Create directory
            File reportDir = new File(directoryPath);
            if (!reportDir.exists()) reportDir.mkdirs();

            // Prepare filename
            String reportName = institutionReportName;
            reportName = reportName.replace("#bankcode", bankCode);
            reportName = reportName.replace("#yymmdd", tools.getYyMmDd());

            // Prepare file path
            String reportPath = directoryPath.concat("/").concat(reportName);
            // Create file
            File reportFile = new File(reportPath);
            if (!reportFile.exists()) {
                try {
                    reportFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                reportFile.delete();
                try {
                    reportFile.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            // Prepare file content
            String reportContent = generateReportContent(role, bankCode, bankName, product);

            // Write content to file
            FileWriter fw = null;
            try {
                fw = new FileWriter(
                        reportPath, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PrintWriter pw = new PrintWriter(fw);
            pw.println(reportContent);
            pw.close();
            System.out.println("Report generated:" + reportPath);
        }
    }

    public static String generateReportContent(String role, String bankCode, String bankName, String product) {
        StringBuilder content = new StringBuilder();

        // Add header
        String header = generateReportHeader(role, bankCode, bankName, product);
        // Add body
//        String body = generateReportBody();
        // Add trailing
//        String trailing = generateReportTrailing();

        content.append(header);
//        content.append(body);
//        content.append(trailing);
        return content.toString();
    }

    public static String generateReportHeader(String role, String bankCode, String bankName, String product) {
        /*Row 1*/
        String data = String.format("%-1s", "");
        data += "+";
        data += createLine(130);
        data += "+";
        data += "\n";
        /*Row 2*/
        data += "1";
        data += String.format("%-1s", "");
        data += padSpaceRight("RETENSI", 10);
        data += ":";
        data += String.format("%-1s", "");
        data += padSpaceRight("RA.1B/6B/2T", 32);
        data += padSpaceRight("LAPORAN TRANSAKSI EFT SWITCHING ATM-PAYMENT", 66);
        data += padSpaceRight("FREKWENSI :   HARIAN", 20);
        data += "\n";
        /*Row 3*/
        data += String.format("%-2s", "");
        data += padSpaceRight(String.format("LAPORAN   : %s", product), 44);
        data += padSpaceRight(String.format("%s", role), 8);
        data += ":";
        data += String.format("%-1s", "");
        data += padSpaceRight(String.format("%s", bankName), 56);
        data += padSpaceRight("TANGGAL", 10);
        data += ":";
        data += String.format("%-1s", "");
        data += tools.getDdMmYy();
        data += "\n";
        /*Row 4*/
        data += String.format("%-112s", "");
        data += padSpaceRight("HALAMAN", 10);
        data += ":";
        data += String.format("%-8s", "");
        data += "1";
        data += "\n";
        /*Row 5*/
        data += "0";
        data += "+";
        data += createLine(130);
        data += "+";
        data += "\n";
        /*Row 6*/
        data += String.format("%-3s", "");
        data += padSpaceRight("NO.", 7);
        data += padSpaceRight("TANGGAL", 10);
        data += padSpaceRight("JAM", 8);
        data += padSpaceRight("TX-CD", 7);
        data += padSpaceRight("TYPE", 8);
        data += padSpaceRight("NO-KARTU", 33);
        data += padSpaceRight("AMOUNT", 30);
        data += padSpaceRight("AUTH-RESP", 18);
        data += padSpaceRight("TRACE-NO", 8);
        data += "\n";
        /*Row 7*/
        data += String.format("%-10s", "");
        data += padSpaceRight("REF-NO", 33);
        data += padSpaceRight("TERM-ID", 22);
        data += padSpaceRight("MCC - MERCHANT-ID", 23);
        data += padSpaceRight("MERCHANT", 27);
        data += "\n";
        /*Row 8*/
        data += String.format("%-10s", "");
        data += "RCODE";
        data += "\n";
        /*Row 9*/
        data += String.format("%-1s", "");
        data += "+";
        data += createLine(130);
        data += "+";
        data += "\n";
        return data;
    }

    public static String createLine(int line) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < line; i++) {
            sb.append("-");
        }
        return sb.toString();
    }

    public static String padSpaceRight(String data, int len) {
        return String.format("%-" + len + "." + len + "s", data);
    }

}
