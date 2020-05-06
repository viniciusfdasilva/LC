
public class Log{
    public static int cont = 1;

    public static void printLog(String message){
        System.out.println("LOG " + cont + " : [" + message + "]");
        cont++;
    }//End print()
}// End Log