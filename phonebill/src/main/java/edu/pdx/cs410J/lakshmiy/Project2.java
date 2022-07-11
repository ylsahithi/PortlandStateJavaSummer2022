package edu.pdx.cs410J.lakshmiy;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Project2 {
        /**  Public variables to pass error strings to print error function
         * These variables are used in test case validations
         *
         */
        public static final String Missing_args = "No args provided to run, required atleast 7 args";
        public static final String Invalid_args = "Invalid arguments passed";
        public static final String Invalid_options = "Invalid options provided as arguments";
        public static final String No_Print_args = "No arguments passed to print";
        public static final String Less_Num_args = "less number of arguments passed cannot execute the class";
        public static final String More_Num_args = "Too Many arguments for the class";
        public static final String Readme_txt = "This is a README file! Readme.txt";
        public static final String text_File = "Written data to text file";
        public static final String text_File_error = "Missing text file and arguments ";
    public static final String File_not_found = "Exception caught file not found";
    public static final String Empty_file = " file is empty, cannot parse empty file ";



        /**
         * author @yalam2@pdx.edu
         * Main function for the application
         * args are in the format : "opt1" " opt2" "name" "callee number"
         * "caller number" "begin time" "end time"
         * -option1 -option2 (-readme -print)
         *  optional args
         * -name
         *  Customer name
         *  -caller number
         *  caller phone number
         *  -callee number
         *    callee phone number
         *  - begin time
         *     Start time for the phone call
         *  - end time
         *      End time for phone call
         * @param args
         *
         *
         */
        public static void main(String[] args) throws IOException, ParserException {
//            for (String arg: args){
//                System.out.println(arg);
//            }
            validateInputArgsCount(args);
//            parseAndDump(args,0,3 );
//            PhoneCall callData = new PhoneCall(args, 0);
//            PhoneBill cust = new PhoneBill(args[0], callData);
//            PhoneBill pb;
//            File textFile = new File("project2.txt");
//            TextParser parse = new TextParser(new FileReader(textFile));
//            pb = (PhoneBill) parse.parse();
//            pb.addPhoneCall(callData);
//            System.out.println("get calls");
//            System.out.println(pb.getPhoneCalls());
//            System.out.println(pb.toString());
//            TextDumper dump = new TextDumper("project2.txt");
//            dump.dump(pb);

        }


        public static void parseAndDump(String [] args, int filePos, int argPos) throws IOException, ParserException{

                PhoneCall callData = new PhoneCall(args, argPos);
                String file = args[filePos].split(" ")[1];

                File textFile = new File(file);

//                FileReader fr = new FileReader(textFile);
                if (textFile.length() == 0) {
                    textFile.createNewFile();
                    PhoneBill pb = new PhoneBill(args[argPos]);
                    System.out.println("dumper");
                    pb.addPhoneCall(callData);

                    TextDumper dump = new TextDumper(file);
                    dump.dump(pb);
                    printErrorMessage(text_File);
                }
                else {
                    FileReader fr = new FileReader(textFile);
//                InputStream resource = InputStream.class.getResourceAsStream(file);

//                TextParser parser = new TextParser(new InputStreamReader(resource));
                    TextParser parse = new TextParser(fr, args[argPos]);

//                TextParser parse = new TextParser(new InputStreamReader(resource));
                    System.out.println("dumper");
                    PhoneBill pb = (PhoneBill) parse.parse();
                    System.out.println("dumper");
                    pb.addPhoneCall(callData);

                    TextDumper dump = new TextDumper(file);
                    dump.dump(pb);
                    printErrorMessage(text_File);
                }
//                System.out.println("dumper");
//                pb.addPhoneCall(callData);
//
//                TextDumper dump = new TextDumper(file);
//                dump.dump(pb);
//                printErrorMessage(text_File);
            }
//            catch (IOException | ParserException PO){
//                System.err.println(File_not_found);
//                System.err.println("exception in main class ");
//            }
//        }
        /**
         * This function returns void and prints contents of readme file
         * It is called when user arguments has readme option
         * return void
         *
         */
        public static void printREADMEOption(){
            BufferedReader br = null;
            try {
                InputStream ReadmeFile = edu.pdx.cs410J.lakshmiy.Project1.class.getResourceAsStream("README.txt");
                InputStreamReader temp = new InputStreamReader(ReadmeFile);
                br = new BufferedReader(temp);
                String lines;
                while ((lines = br.readLine()) != null) {
                    System.out.println(lines);
                }
                printErrorMessage(Readme_txt);
                return;
            } catch (IOException IO) {
                System.err.println("No file found on source system");
            }
        }


        /**
         *  This method is used to print error message in the application
         *  @param  message
         *
         */
        public static void printErrorMessage(String message) {
            System.err.println(message);
            return;
        }

        /**
         *  This method validated number of arguments passed by user,
         *  it returns false and appropriate error message if invalid arguments are passed
         *  return type is  boolean
         *  @param  args
         *
         */
        public static void validateInputArgsCount(String[] args) throws ParserException, IOException {
            ArrayList list = new ArrayList<>();
            String inputFileArg = "";
            for (String arg :args) {
                if (arg.startsWith("-textFile")) {
                    inputFileArg = arg;
                }
                list.add(arg);
            }
            ArrayList optionSet = new ArrayList<>();
            /**
             * If there are no arguments passed to main function
             */
            if (args.length == 0) {
                printErrorMessage(Missing_args);
                return;
            }
            /**
             * If readme is one of the arguments then it prints readme and exit
             * prints read me in system error
             */
            if(list.contains("-readme") && list.indexOf("-readme") <= 3){
                printREADMEOption();
                return;
            }
            /**
             * If there are only one argument passed if the argument is type
             * of optional arguments it returns error message
             */

            else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("-print")) { printErrorMessage(No_Print_args);  return;}
                else if (args[0].equalsIgnoreCase(inputFileArg)) { printErrorMessage(text_File_error);  return;}
                else {
                    printErrorMessage(Invalid_args);
                    return;
                }
            }
            else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("-print")||args[1].equalsIgnoreCase("-print")) { printErrorMessage(No_Print_args);  return;}
                else if (args[0].equalsIgnoreCase(inputFileArg)||args[1].equalsIgnoreCase(inputFileArg)) { printErrorMessage(text_File_error);  return;}
                else {
                    printErrorMessage(Invalid_args);
                    return;
                }
            }


            /**
             * If more than 10 args are passed
             */
            else if (args.length > 10) {
                printErrorMessage(More_Num_args);
                return;
                /**
                 * If more than 2 and less than 7 args are passed
                 */
            } else if ((args.length < 7) && (args.length > 2)) {
                printErrorMessage(Less_Num_args);
                return;
                /**
                 * If valid arguments are passed in expected order.
                 */
            } else if (args.length == 7) {
                if ((list.contains("-print") || list.contains(inputFileArg) || list.contains("-readme"))) {
                    printErrorMessage(Less_Num_args);
                    return;
                }
                else {
                    if (!validateEachArg(args)) {
                        printErrorMessage(Invalid_args);
                        return;
                    } else {
                        PhoneCall callData = new PhoneCall(args,0);
                        PhoneBill cust = new PhoneBill(args[0], callData);
                        System.err.println(callData.toString());
                    }
                }
            } else if (args.length == 8) {
                if (!validateEachArg(args)) {
                    printErrorMessage(Invalid_args);
                    return;
                } else {
                    if (args[0].equalsIgnoreCase("-print")) {
                        PhoneCall callData = new PhoneCall(args, 1);
                        PhoneBill cust = new PhoneBill(args[1], callData);
                        System.out.println(callData.toString());
                        printErrorMessage(callData.toString());
                    }
                    else if (args[0].equalsIgnoreCase(inputFileArg)) {
                        parseAndDump(args, 0, 1);
                        printErrorMessage(text_File);
                    }
                    if (!(list.contains("-print") || list.contains(inputFileArg) || list.contains("-readme"))) {
                        printErrorMessage(Invalid_options);
                    }

                }
            }
            else if (args.length == 9) {
                if (!validateEachArg(args)) {
                    printErrorMessage(Invalid_args);
                    return;
                }
                else {
                    if (list.contains("-print") && list.indexOf("-print") <= 2) {
                        PhoneCall callData = new PhoneCall(args, 2);
                        PhoneBill cust = new PhoneBill(args[2], callData);
                        System.out.println(callData.toString());
                        printErrorMessage(callData.toString());
                    }
                    if (list.contains(inputFileArg) && list.indexOf(inputFileArg) <= 2) {
                        int pos = list.indexOf(inputFileArg);
                        parseAndDump(args, pos,2);
                    }
                    if (!(list.contains("-print") || list.contains(inputFileArg) || list.contains("-readme"))) {
                        printErrorMessage(Invalid_options);
                    }
                }
            }
            else if (args.length == 10) {
//                System.out.println(list.contains("-textfile"));
                if (!validateEachArg(args)) {
                    printErrorMessage(Invalid_args);
                    return;
                } else {
                    if (!(list.contains("-print") || list.contains(inputFileArg) || list.contains("-readme"))) {
                        System.out.println("args invalid");
                        printErrorMessage(Invalid_options);
                    }
                        if (list.contains("-print") && list.indexOf("-print") <= 3) {
                            System.out.println("print optin");
                            PhoneCall callData = new PhoneCall(args, 3);
                            PhoneBill cust = new PhoneBill(args[3], callData);
                            System.out.println(callData.toString());
                            printErrorMessage(callData.toString());
                        }
                        if (list.contains(inputFileArg) && list.indexOf(inputFileArg) <= 3) {
                            System.out.println(list.indexOf(inputFileArg));
                            int pos = list.indexOf(inputFileArg);
                            parseAndDump(args, pos,3);
                        }
                }
            }
            return;
        }



        /**
         * This method validates each argument individually and returns true if arguments are valid
         * it checks for valid phone numbers date and time in input args return type is boolean
         * @param args
         *
         */
        @VisibleForTesting
        public static boolean validateEachArg(String[] args) {
            if (args.length == 7){
                if ((checkForvalidString(args[0])) && (isValidPhoneNumber(args[1])) && (isValidPhoneNumber(args[2])) && (checkForValidDate(args[3])) && (checkForValidTime(args[4]))
                        && (checkForValidDate(args[5])) && (checkForValidTime(args[6]))) {
                    return true;
                }
            }
            else  if (args.length == 8) {
                if ((checkForvalidString(args[1])) && (isValidPhoneNumber(args[2])) && (isValidPhoneNumber(args[3])) && (checkForValidDate(args[4])) && (checkForValidTime(args[5]))
                        && (checkForValidDate(args[6])) && (checkForValidTime(args[7]))) {
                    return true;
                }
            }
            else  if (args.length == 9) {
                if ((checkForvalidString(args[2])) && (isValidPhoneNumber(args[3])) && (isValidPhoneNumber(args[4])) && (checkForValidDate(args[5])) && (checkForValidTime(args[6]))
                        && (checkForValidDate(args[7])) && (checkForValidTime(args[8]))) {
                    return true;
                }
            }
            else  if (args.length == 10) {
                if ((checkForvalidString(args[3])) && (isValidPhoneNumber(args[4])) && (isValidPhoneNumber(args[5])) && (checkForValidDate(args[6])) && (checkForValidTime(args[7]))
                        && (checkForValidDate(args[8])) && (checkForValidTime(args[9]))) {
                    return true;
                }
            }
            return false;
        }


        /**
         * This method validates each customer name argument, it checks for valid string as input
         * return type is boolean
         * @param name
         *
         */
        @VisibleForTesting
        public static boolean checkForvalidString(String name) {
            if (name.trim().isEmpty() || name.length() == 1 || (name.replaceAll("[^a-zA-Z]", "").length() == 0)) {
                printErrorMessage("Invalid customer name");
                return false;
//      return false;
            }
            return true;
        }


        /**
         * This method validates phone number value of the arguments
         * return type is boolean
         * @param phoneNumber
         *
         */
        @VisibleForTesting
        static boolean isValidPhoneNumber(String phoneNumber) {
            if (phoneNumber.length() < 10) {
                printErrorMessage("Invalid phone number, number of digits less than 10");
                return false;
            } else if (phoneNumber.startsWith("0")) {
                printErrorMessage(" Invalid phone number, a phone number cannot start with zero");
                return false;
            } else if (!phoneNumber.matches("[0-9]+")) {
                printErrorMessage("Invalid input for phone number, it cannot contain letters");
                return false;
            }
            return true;
        }


        /**
         * This method validates DAte value in args checks fi date format is as expected
         * return type is boolean
         * @param date
         *
         */
        @VisibleForTesting
        public static boolean checkForValidDate(String date) {
            try {
                SimpleDateFormat validFormat = new SimpleDateFormat("MM/dd/yyyy");
                Date formattedDate = validFormat.parse(date);
                System.out.println(formattedDate);
                if (date.equals(validFormat.format(formattedDate))) {
                    return true;
                } else {
                    printErrorMessage("Invalid input for date");
                    return false;
                }
            } catch (ParseException PE) {
                printErrorMessage("Invalid input for date");
                return false;
            }
        }

        /**
         * This method validates Time value in args checks if it is valid
         * return type is  boolean
         * @param time
         *
         */
        @VisibleForTesting
        public static boolean checkForValidTime(String time) {
            try {
                String[] hourMin = time.split(":");
                if ((Integer.parseInt(hourMin[0]) < 24) && (Integer.parseInt(hourMin[1]) < 60)) {
                    return true;
                }
                printErrorMessage("Invalid input for time");
                return false;
            } catch (Exception e) {
                return false;
            }
        }

}
