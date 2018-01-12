package we.com.test1;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

/**
 * Created by Ming on 2018/1/10.
 */

public class LoginToServer {
    String urlAddress="http://100.133.30.54:8080/login/LoginServlet";  //写明希望沟通的服务器的地址，此处为本机的Tomcat服务器
    HttpClient httpClient=new DefaultHttpClient();  //一般继承DefaultClient以实例化使用


    public String doGet(String username,String password){   //doGet方法向服务器发送请求,想向服务器发送数据
        String getURL=urlAddress+"?username="+username+"&password="+password;

        HttpPost httpGet=new HttpPost(getURL);
        String response="";
        try{
            HttpResponse httpResponse=httpClient.execute(httpGet);//通过execute方法向服务器请求，服务器返回一个httpresponse对象
                                                                  //httpResponse中封装了HTTP响应信息，包括了响应信息，响应状态，响应内容
            if (httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK){ //判断响应的返回结果是不是200(有没有和服务器连上)
                HttpEntity httpEntity=httpResponse.getEntity();  //将响应消息封装成Entity实体类型
                InputStream inputStream=httpEntity.getContent();  //将Entity实体类型再度通过getContent方法变为输入型比特流
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"GBK"));
                String readLine=null;

                while((readLine=bufferedReader.readLine())!=null){  //BufferedReader的功能待深入研究
                    response=response+readLine;
                }
                inputStream.close();  //关闭输入流

            }//if over
            return response;   //若链接成功，上述动作后，至此返回响应结果

        }catch (Exception e){
            e.printStackTrace();
            return "exception";
        }
    }//doGet over
    public String doPost(String username,String password){  //采用doPost方法发送请求
        HttpPost httpPost=new HttpPost(urlAddress);

        List<NameValuePair> params=new ArrayList<NameValuePair>();  //采用ArrayList存放键值对格式的数据
        NameValuePair pair1=new BasicNameValuePair("username",username);
        NameValuePair pair2=new BasicNameValuePair("password",password);
        params.add(pair1);
        params.add(pair2);                  //至此params携带了用户名和密码的信息

        HttpEntity httpEntity;
        try{
            httpEntity=new UrlEncodedFormEntity(params,"gbk");//通过此方法将信息封装成httpEntity实体类型
            httpPost.setEntity(httpEntity);        //设置请求实体，参数为带有用户名密码信息的实体型httpEntity，可理解为httpPost此时也带有了信息
            HttpResponse httpResponse=httpClient.execute(httpPost);  //借助httpClient的execute方法将httpPost发向服务器端，并返回一个响应信息
            //httpResponse 携带了响应信息，但是还需要一些步骤才能切实地得到信息
            if (httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                HttpEntity httpEntity1=httpResponse.getEntity(); //把响应信息中的实体型取出来
                InputStream inputStream=httpEntity1.getContent();//getContent方法获取到了信息，但是形式是二进制流
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));//输入流读取类InputStreamReader把上述二进制流作为参数
                                                                                                     //读取并交给新建的缓冲读取的构造函数,使得bufferedReader携带了响应信息
                String response="";
                String readLine=null;
                while((readLine=bufferedReader.readLine())!=null){
                    response=response+readLine;                     //BufferedReader.readLine()读取字符进缓冲区。*当读取到换行符时，？一次将整行字符串传入
                }
                inputStream.close();  //只有关闭输入流后，BufferedReader.readLine()才会返回NULL
                return response;
            }
            else {return "error";}
        }catch (Exception e1){
            e1.printStackTrace();
            return "exception";
        }
   }



}
