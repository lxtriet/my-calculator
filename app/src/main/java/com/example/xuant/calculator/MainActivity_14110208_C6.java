package com.example.xuant.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Stack;

// Lâm Xuân Triết
// 14110208
// Lập Trình Di Động Chiều Thứ 6

public class MainActivity_14110208_C6 extends AppCompatActivity {

    private TextView screen;  // màn hình
    private String display = "";      // Chuỗi chứa biểu thức
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_14110208__c6);
        screen = (TextView) findViewById(R.id.textView);
        screen.setText(display);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    private void updateScreen() {          // Hàm cập nhật và tô màu màn hình
        screen.setText("");  // Xóa màn hình
        if(display.length()>0)
        {
            for(int i=0;i<display.length();i++)   // Cho chạy từ đầu tới cuối biểu thức để tô màu từng kí tự
            {
                SpannableString spannableString = new SpannableString(String.valueOf(display.charAt(i)));
                if(String.valueOf(display.charAt(i)).matches("[\\+\\–\\[\\]÷\\[\\]x]"))       // Tô màu cho toán tử
                {
                    ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorTxt_Oper));
                    spannableString.setSpan(foregroundSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                else        // Tô màu cho toán hạng
                {
                    ForegroundColorSpan foregroundSpan2 = new ForegroundColorSpan(getResources().getColor(R.color.colorTxt_Num));
                    spannableString.setSpan(foregroundSpan2, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                screen.append(spannableString);
            }
        }
        else
            screen.setText(display);
    }

    protected void onClickNumber(View v) {  // Sự kiện click số
        Button b = (Button) v;
        if (display.length() > 0) {
            int dem=0;
            for(int i=display.length()-1;i>=0;i--)        // Vòng for để đếm số số 0
            {
                if(String.valueOf(display.charAt(i)).equals("0"))   // Kiểm tra và đếm số số 0
                    dem++;
                else
                {
                    if(String.valueOf(display.charAt(i)).matches("[\\+\\–\\[\\]÷\\[\\]x\\(\\)]"))  // Gặp toán tử thì dừng lại
                        break;
                    else
                    {
                        dem=0;break;
                    }
                }
            }
            if (!String.valueOf(display.charAt(display.length() - 1)).matches("[\\)]") && dem==0)    // Nếu kí tự cuối không là ) và số số 0 là 0 thì thêm số vào
                display += b.getText();
            if(dem!=0)          // Nếu số số 0 không bằng không thì thay số 0 bằng số mới
            {
                display=display.substring(0,display.length()-1);
                display += b.getText();
            }
        } else
            display += b.getText();

        if(display.length()==15)     // Biểu thức quá dài thì giảm size chữ lại
            screen.setTextSize(30);
        updateScreen();
    }

    private boolean CheckOperator() {   // Hàm kiểm tra toán tử
        boolean flag = false;   // Tạo cờ
        String temp = String.valueOf(display.charAt(display.length() - 1));
        if (temp.matches("[\\+\\–\\[\\]÷\\[\\]x]")) // Nếu phía trước là toán tử thì cờ chuyển sang true
            flag = true;
        return flag;
    }

    protected void onClickOperator(View v) {   // Sự kiện click toán tử
        Button b = (Button) v;
        String c = b.getText().toString();
        if (display.length() > 0)
        {
            if(c.matches("[\\+\\–]") && !String.valueOf(display.charAt(display.length() - 1)).matches("[\\.]"))  // Nếu là + hoặc trừ và phía trước không phải dấu .
            {
                if (CheckOperator())  // Kiểm tra phía trước có phải toán tử
                    display = display.substring(0, display.length() - 1); // Phải thì thay thế
                display +=b.getText();
            }
            else         // Nếu là x hoặc ÷
            {
                if(!String.valueOf(display.charAt(display.length() - 1)).matches("[\\.\\(]"))   // Đằng trước không phải là dấu .
                {
                    if(String.valueOf(display.charAt(display.length() - 1)).matches("[\\+\\–\\[\\]x\\[\\]÷]"))   // Kiểm trước đằng trước có phải toán tử
                    {
                        if(!String.valueOf(display.charAt(display.length() - 2)).matches("[\\(]"))    // Kiểm tra đằng trước toán tử có phải là dấu (
                        {
                            if (CheckOperator())     // Nếu không phải thì thay kiểm tra rồi thay thế
                                display = display.substring(0, display.length() - 1);
                            display +=b.getText();
                        }
                    }
                    else
                        display +=b.getText();
                }
            }
        }
        if(display.length()==15)  // Nếu chuỗi quá dài thì giảm nhỏ size chữ lại
            screen.setTextSize(30);
        updateScreen();
    }

    private void Clear() {   // hàm xóa màn hình
        display = "";
        screen.setTextSize(40);  // trả lại size chữ ban dầu
    }

    protected void onClickClear(View v) {  // Sự kiện click xóa
        Clear();
         screen.setTextSize(40);
         updateScreen();

    }

    public int priority(char c) {        // Hàm trả về thứ tự ưu tiên của toán tử
        if (c == '+' || c == '–') return 1;
        else if (c == 'x' || c == '÷') return 2;
        else return 0;
    }

    public boolean isOperator(char c) {  // Hàm kiểm tra toán tử
        if(String.valueOf(c).matches("[\\+\\–\\[\\]x\\[\\]÷\\(\\)]"))
            return true;
        return false;
    }

    public String[] processString(String chuoi) {     // Hàm chuẩn hóa biểu thức nhập vào trả về 1 mảng string
        String s = "", elementMath[] = null;
        chuoi=chuoi.replaceAll(",","");
        for (int i = 0; i < chuoi.length(); i++) {
            char c = chuoi.charAt(i);
            if (!isOperator(c))
                s = s + c;
            else s = s + " " + c+" ";
        }
        s = s.trim();
        s = s.replaceAll("\\s+"," ");
        elementMath = s.split(" ");  // Tách chuỗi chuẩn hóa ra mảng
        return elementMath;
    }

    public String[] postfix(String[] chuoi) {      // Hàm chuyển mảng biểu thức thành mảng postfix để tính toán

        Stack<String> stack = new Stack<String>();
        String s="",a[];
        String c;

        for (int i = 0; i < chuoi.length; i++) {
            c = String.valueOf(chuoi[i].charAt(0));
            if (!c.matches("[\\+\\–\\[\\]x\\[\\]÷\\(\\)]")) { // Nếu không phải toán tử thì cộng vào chuỗi s
                s+=chuoi[i]+" ";
            } else if (c == "(") {  // nếu là toán tử thì push vào stack
                stack.push(c);
            }
            else if (c == ")") {  // nếu là toán tử thì push vào stack

                while (!stack.isEmpty() && stack.peek() != "(") {  // Kiểm tra biểu thức trong 2 dấu ( )
                    s+=stack.pop()+" ";
                }
                if (!stack.isEmpty() && stack.peek() != "(")
                    return null;
                else if(!stack.isEmpty())
                    stack.pop();
            }
            else if (isOperator(c.charAt(0)))        // Nếu là toán tử
            {

                if (!stack.isEmpty() && priority(c.charAt(0)) <= priority(stack.peek().charAt(0))) {  // Khi stack không rỗng và độ ưu tiên của toán tử hiện tại nhỏ hơn hoặc bằng độ ưu tiên của toán tử ở top trong stack
                    s+=stack.pop()+" ";    // Lấy toán tử top trong stack cộng vào chuỗi s
                }
                stack.push(c);  // đẩy toán tử vào stack
            }
        }

        while (!stack.isEmpty()) {      // Lấy hết toán tử còn lại trong stack cộng vào chuỗi s
            String t = stack.pop();
            s+=t+" ";
        }
        s=s.replace("(","");   // Loại bỏ những kí tự dư thừa
        s=s.replace(")","");
        s=s.trim();
        a=s.split(" ");   // Tách chuỗi thành mảng String  ---> chuỗi postfix
        return a;
    }

    public String valueMath(String[] elementMath) {    // Hàm tính toán chuỗi postfix
        Stack<String> S = new Stack<String>();
        for (int i = 0; i < elementMath.length; i++) {  // Chạy thứ tự từ đầu đến cuối chuỗi postfix
            char c = elementMath[i].charAt(0);
            if (!isOperator(c)) S.push(elementMath[i]);  // Nếu không là toán tử thì đẩy vào stack
            else {                        // Nếu là toán tử thì lấy 2 phần tử top-1 và top-2 ra tính toán với toán tử
                double num = 0f;
                double num1 = Double.parseDouble(S.pop());
                double num2 = 0;
                if(!S.empty())       // Trường hợp số âm thì num2 sẽ là 0
                    num2=Double.parseDouble(S.pop());
                switch (c) {   // Thực hiện tính toán ứng với các toán tử
                    case '+':
                        num = num2 + num1;
                        break;
                    case '–':
                        num = num2 - num1;
                        break;
                    case 'x':
                        num = num2 * num1;
                        break;
                    case '÷':
                        num = num2 / num1;
                        break;
                    default:
                        break;
                }
                S.push(Double.toString(num));
            }
        }
        return S.pop();
    }

    public String FormatNumber(String x)   // Format số có chấm , ở phần ngàn
    {
        return NumberFormat.getNumberInstance(Locale.US).format(Float.parseFloat(x)).toString();
    }
    protected void onClickEqual(View v) {    // Sự kiện Click bằng
       try{
           if(!String.valueOf(display.charAt(display.length()-1)).matches("[\\+\\–\\[\\]x\\[\\]÷\\.]"))  // Khi kí tự cuối không phải là + - x ÷ và . thì mới bấm = được
           {
               for(int i=0;i<CheckBraket();i++)  // Tự động thêm vào biểu thức những ngoặc đóng thiếu
                   display+=")";
               updateScreen();  // Cập nhật lại màn hình
               SpannableString spannableString = new SpannableString("="+FormatNumber(valueMath(postfix(processString(display))).toString()));  // Tô màu cho kết quả xuất ra
               ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorTxt_Result));
               spannableString.setSpan(foregroundSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


               screen.append("\n");   // Đẩy lên textview
               screen.append(spannableString);
           }
       }
       catch (Exception e){  // Bắt nếu có lỗi xảy ra
           SpannableString spannableString = new SpannableString("Sai định dạng");
           ForegroundColorSpan foregroundSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorAccent));
           spannableString.setSpan(foregroundSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
           screen.setText("");
           screen.append(spannableString);
       }

    }

    protected void onClickBackSpace(View v) {  // Sự kiện click backspace
        if(display.length()>0)
        {
            display = display.substring(0, display.length() - 1);  // Xóa 1 phần tử cuối
            if(display.length()<15)
                screen.setTextSize(40);
        }
        updateScreen();
    }

    protected void onClickDot(View v) {   // Sự kiện click dấu chấm
        int countDot = 0;
        if (display.length() > 0) {   // Kiểm tra có biểu thức có phần tử chưa
            if (!String.valueOf(display.charAt(display.length() - 1)).matches("[\\+\\–\\[\\]÷\\[\\]x\\)\\(\\.]")) {  // kiểm tra kí tự đằng trước không phải là toán tử hay dấu .
                for (int i = display.length() - 1; i >= 0; i--) {  // Vòng for đếm xem số hiện tại có dấu chấm nào chưa
                    if (String.valueOf(display.charAt(i)).matches("\\."))
                        countDot++;
                    if (String.valueOf(display.charAt(i)).matches("[\\+\\–\\[\\]÷\\[\\]x\\)\\(]"))
                        break;
                }
                if (countDot == 0)   // Nếu không có thì mới thêm dấu chấm vào được
                    display += ".";
            }
        } else   // chưa có thì mặc định dấu chấm là 0.
            display += "0.";
        if(display.length()==15)    // Nếu chuỗi dài thì giảm size chữ lại
            screen.setTextSize(30);
        updateScreen();
    }

    public int CheckBraket() {   // Hàm trả về số ngoặc đóng thiếu
        int BraketOpen, BraketClose;
        BraketOpen = 0;
        BraketClose = 0;
        for (int i = 0; i < display.length(); i++) {
            if (String.valueOf(display.charAt(i)).matches("\\("))  // Đếm số ngoặc mở
                BraketOpen++;
            if (String.valueOf(display.charAt(i)).matches("\\)"))   // Đếm số ngoặc đóng
                BraketClose++;
        }
        return BraketOpen-BraketClose ; // nếu braClose = BraOpen ==> true
    }

    protected void onClickBraket(View v) {   // Sự kiện click đóng mở ngoặc
        if (display.length() > 0) {    // Kiểm tra biểu thức có phần tử chưa
            if (String.valueOf(display.charAt(display.length() - 1)).matches("[\\+\\–\\[\\]÷\\[\\]x\\(]"))   // Kiểm tra kí tự đằng trước có phải toán tử hay dấu mở ngoặc thì mới mở ngoặc được
                display += "(";
            else if (!String.valueOf(display.charAt(display.length() - 1)).matches("[\\+\\–\\[\\]÷\\[\\]x\\(\\.]") && CheckBraket()>0)  // Ngược lại và số ngoặc mở nhiều hơn ngoặc đóng thì mới đóng ngoặc
                display += ")";
        } else  // Nếu không có phần tử nào thì mở ngoặc
            display += "(";
        if(display.length()==15)   // Nếu chuỗi dài quá thì giảm size chữ lại
            screen.setTextSize(30);
        updateScreen();
    }

    protected void onClickSomething(View v)    // Sự kiện click âm dương
    {
//        SpannableString spannableString = new SpannableString("Hello World!");
//        BackgroundColorSpan backgroundSpan = new BackgroundColorSpan(Color.YELLOW);
//        spannableString.setSpan(backgroundSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        screen.append(spannableString);
        int n=0;
        String temp="";
        int l = display.length();
        try{
            if(display.length()>0)   // Kiểm tra chuỗi có phần tử chưa
            {
                for(int i=l-1;i>=0;i--)  // Vòng for để đếm độ dài của số hiện tại
                {
                    if(!String.valueOf(display.charAt(i)).matches("[\\+\\–\\[\\]x\\[\\]÷\\)\\(]"))
                        n++;
                    else break;
                }
                if(n!=0) temp=display.substring(display.length()-n,display.length());  // Nếu có số thì cắt số ra khỏi biểu thức
                if(n==display.length()) display="";   // Nếu biểu thức chỉ có 1 số thì reset lại biểu thức
                else    display=display.substring(0,display.length()-n);  // Ngược lại thì biểu thức = biểu thức loại bỏ số cuối
                if(display.length()>0)
                {

                    if(String.valueOf(display.charAt(display.length()-1)).equals("–"))  // Nếu kí tự cuối của biểu thức là dấu -
                    {
                        if(String.valueOf(display.charAt(display.length()-2)).equals("("))   // Kí tự tiếp theo là dấu (
                            display=display.substring(0,display.length()-2);  // Thì số hiện tại là số âm --> bỏ đi kí tự (- của biểu thức
                    }
                    else     // Số hiện tại là số dương -- > Thêm kí tự (-
                        display+="(–";
                }
                else    // Biểu thức chưa có số thì thêm (-
                    display+="(–";

            }
            else   // Biểu thức chưa có số thì thêm (-
                display+="(–";
            display+=temp;
        }
        catch (Exception e)
        {
            display="Sai định dạng";
        }
        if(display.length()==15)  // Biểu thức quá dài thì giảm size chữ lại
            screen.setTextSize(30);
        updateScreen();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

}
