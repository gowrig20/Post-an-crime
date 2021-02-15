import java.io.PrintWriter; import java.net.ServerSocket; import java.net.Socket; import java.sql.*;
import java.util.ArrayList; import java.util.HashMap; import java.util.StringTokenizer; import java.net.URLDecoder; import java.net.URLEncoder;
import java.io.BufferedOutputStream; import java.io.FileOutputStream; import java.io.IOException;
import java.io.InputStream; import java.io.ObjectInputStream;
import java.io.ObjectOutputStream; import java.io.OutputStream;
public class MainServer implements Runnable{



    static ServerSocket serverSocket = null; static Socket socket = null;
    private Connection con=null; private Statement stmt=null;

    private ResultSet rs=null; StringBuilder sb=new StringBuilder();
    private String dbRecords = "",purchaseList="",ccNumber,pinNumber; private StringTokenizer st = null;
    int availableBalance = 0,purchaseAmt = 0, totalBalance = 0; boolean cardAuthenticaionflag = false,balanceflag= false; boolean status=false;
    PrintWriter out = null;



//Image

    public MainServer(){ try{
        serverSocket = new ServerSocket(5555); System.out.println("Server started	5555");
    }catch(Exception ex){ ex.printStackTrace(); System.out.println(ex);
    }

    }

    public void run(){ try{
        out = new PrintWriter(socket.getOutputStream());

        String str = HttpTunnel.undoHttpTunneling(socket.getInputStream()); str = str.substring(1);
        str=URLDecoder.decode(str, "UTF-8"); System.out.println("Value.	"+str);


// HttpTunnel.doHttpTunneling(out,"OK"); con = getConnection();
        if(con == null){ System.out.println("Connection Not Found");
            System.exit(0);

        }

        if(str.startsWith("ADD")){ st=new StringTokenizer(str,"$"); st.nextToken();
            String name=st.nextToken(); String uname=st.nextToken(); String pass=st.nextToken(); String aadhar=st.nextToken(); String email=st.nextToken(); String cno=st.nextToken(); String location=st.nextToken();
//st.nextToken();

            stmt = con.createStatement();

            int i = stmt.executeUpdate("insert into userdetails values('"+name+"','"+uname+"','"+pass+"','"+aadhar+"','"+email+"','"+cno+"','"
                    +lo cation+"')"); if(i>0){
                HttpTunnel.doHttpTunneling(out,"ADDED");

            }

            else{

                HttpTunnel.doHttpTunneling(out,"Not Added");

            }

            System.out.println("DB Records	"+dbRecords);

            HttpTunnel.doHttpTunneling(out,dbRecords); dbRecords="";
        }

        else if(str.startsWith("LOCATION")){ st=new StringTokenizer(str,"$"); st.nextToken();
            String lati=st.nextToken(); String longi=st.nextToken();


//st.nextToken();

            stmt = con.createStatement();

            int i = stmt.executeUpdate("insert into location_details (lati, longi

            )values('"+lati+"','"+longi+"')"); if(i>0){
            HttpTunnel.doHttpTunneling(out,"ADDED");

        }

        else{

            HttpTunnel.doHttpTunneling(out,"Not Added");

        }

        System.out.println("DB Records	"+dbRecords);

        HttpTunnel.doHttpTunneling(out,dbRecords); dbRecords="";
    }

else if(str.startsWith("LOGIN")){ st=new StringTokenizer(str,"$"); st.nextToken();
            String uname=st.nextToken(); String pass=st.nextToken(); stmt=con.createStatement();
            rs=stmt.executeQuery("select name,aadhar from userdetails where uname='"+uname+"' and pass='"+pass+"'");
            if(rs.next()){

                String name=rs.getString(1);

                String aadhar=rs.getString(2); System.out.println(name); System.out.println(aadhar);
// HttpTunnel.doHttpTunneling(out,"VALID$"); HttpTunnel.doHttpTunneling(out,"VALID$"+name+"$"+aadhar);
            }

            else{ HttpTunnel.doHttpTunneling(out,"NOTVALID");
            }

        }

        else if(str.startsWith("POST")){ st=new StringTokenizer(str,"$"); st.nextToken();
            String cname=st.nextToken(); String ctype=st.nextToken(); String location=st.nextToken(); String name=st.nextToken(); String aadhar=st.nextToken(); stmt = con.createStatement();
            int i = stmt.executeUpdate("insert into crime(crimename, crimetype, location,aadhar,name) values('"+cname+"','"+ctype+"','"+location+"','"+aadhar+"','"+name+"')");

            if(i>0){ HttpTunnel.doHttpTunneling(out,"POSTED");
            }

            else{

                HttpTunnel.doHttpTunneling(out,"Not Added");

            }

            System.out.println("DB Records	"+dbRecords);

            HttpTunnel.doHttpTunneling(out,dbRecords); dbRecords="";
        }

        else if(str.startsWith("EMERMANUAL$")){ st=new StringTokenizer(str,"$"); st.nextToken();
            String lati=st.nextToken(); String longi=st.nextToken(); String location=st.nextToken(); stmt = con.createStatement();
            int i = stmt.executeUpdate("insert into risk_area(lati, longi, location) values('"+lati+"','"+longi+"','"+location+"')");
            if(i>0){ HttpTunnel.doHttpTunneling(out,"POSTED");
            }

            else{

                HttpTunnel.doHttpTunneling(out,"Not Added");

            }

            System.out.println("DB Records	"+dbRecords);

            HttpTunnel.doHttpTunneling(out,dbRecords); dbRecords="";
        }

        else if(str.startsWith("GETALLCRIME")){ stmt = con.createStatement();
            rs= stmt.executeQuery("select crimename, crimetype, location, count from crime");
            while(rs.next()){

                String cname=rs.getString(1); String ctype=rs.getString(2); String clocation=rs.getString(3);
                String result="Crime Name:"+cname+"$"+"Crime Type:"+ctype+"$"+"Location:"+clocation+"#";
                sb.append(result); status=true;
//if(status){

// HttpTunnel.doHttpTunneling(out,sb.toString());

// sb=new StringBuilder();

// }

            }

            if(status){ HttpTunnel.doHttpTunneling(out,sb.toString()); sb=new StringBuilder();
            }

            else{

            }

            System.out.println("DB Records	"+dbRecords);

            HttpTunnel.doHttpTunneling(out,dbRecords); dbRecords="";
        }

        else if(str.startsWith("COUNT")){ st=new StringTokenizer(str,"$"); st.nextToken();
            String cname=st.nextToken(); String ctype=st.nextToken(); String clocation=st.nextToken(); stmt = con.createStatement();
            int i = stmt.executeUpdate("update crime set count=count+1 where crimename='"+cname+"' and location='"+clocation+"' ");
            if(i>0){

                HttpTunnel.doHttpTunneling(out,"POSTED");

            }

            else{

                HttpTunnel.doHttpTunneling(out,"Not Added");

            }

            System.out.println("DB Records	"+dbRecords);

            HttpTunnel.doHttpTunneling(out,dbRecords); dbRecords="";
        }

        else if(str.startsWith("FIR")){ st=new StringTokenizer(str,"$"); st.nextToken();
            String cname=st.nextToken(); String name=st.nextToken(); String aadhar=st.nextToken(); stmt = con.createStatement();
            int i = stmt.executeUpdate("insert into fir values('"+cname+"','"+name+"','"+aadhar+"','PENDING')");
            if(i>0){ HttpTunnel.doHttpTunneling(out,"POSTED");
            }

            else{

                HttpTunnel.doHttpTunneling(out,"Not Added");

            }

            System.out.println("DB Records	"+dbRecords);

            HttpTunnel.doHttpTunneling(out,dbRecords); dbRecords="";
        }

        else if(str.startsWith("GETALLLOCATION")){ String location="";


            stmt = con.createStatement();

            rs = stmt.executeQuery("select lati,longi from location_details"); while(rs.next()){ location+=rs.getString(1)+"$"+rs.getString(2)+"#";
            }

            if(!location.equals("")){ HttpTunnel.doHttpTunneling(out,location);
            }

            else{ HttpTunnel.doHttpTunneling(out,"EMPTY");
            }

            System.out.println("DB Records	"+dbRecords);

            HttpTunnel.doHttpTunneling(out,dbRecords);

            dbRecords="";

        }

        else if(str.startsWith("EMERALL")){ String location="";
            stmt = con.createStatement();

            rs = stmt.executeQuery("select lati, longi, location from risk_area"); while(rs.next()){ location+=rs.getString(1)+"$"+rs.getString(2)+"$"+rs.getString(3)+"#";
            }

            if(!location.equals("")){ HttpTunnel.doHttpTunneling(out,location);
            }

            else{ HttpTunnel.doHttpTunneling(out,"EMPTY");
            }



            System.out.println("DB Records	"+dbRecords);

            HttpTunnel.doHttpTunneling(out,dbRecords); dbRecords="";
        }

        else if(str.startsWith("GETSTATUS")){ st=new StringTokenizer(str,"$");

            st.nextToken();

            String name=st.nextToken(); String aadhar=st.nextToken(); stmt = con.createStatement();
            rs= stmt.executeQuery("select cname, statu from fir where name='"+name+"' and aadhar='"+aadhar+"'");
            while(rs.next()){

                String cname1=rs.getString(1); String status=rs.getString(2);
                String result="Crime Name:"+cname1+"$"+"Status:"+status; sb.append(result);


//if(status){

// HttpTunnel.doHttpTunneling(out,sb.toString());

// sb=new StringBuilder();

// }



            }

            if(status){ HttpTunnel.doHttpTunneling(out,sb.toString()); sb=new StringBuilder();
            }

            else{

            }

            System.out.println("DB Records	"+dbRecords);

            HttpTunnel.doHttpTunneling(out,dbRecords); dbRecords="";
        }

        else if(str.startsWith("FINDLOCATION")){ st=new StringTokenizer(str,"$"); st.nextToken();
            String location=st.nextToken(); stmt = con.createStatement();
            rs= stmt.executeQuery("select crimename, crimetype, location, count from crime where count>=3 and location='"+location+"'");
            while(rs.next()){

                String cname=rs.getString(1); String ctype=rs.getString(2); String clocation=rs.getString(3);
                String result="Crime Name:"+cname+"$"+"Crime Type:"+ctype+"$"+"Location:"+clocation+"#";
                sb.append(result); status=true;
//if(status){

// HttpTunnel.doHttpTunneling(out,sb.toString());

// sb=new StringBuilder();

// }

            }

            if(status){ HttpTunnel.doHttpTunneling(out,sb.toString()); sb=new StringBuilder();
            }

            else{

            }

            System.out.println("DB Records	"+dbRecords);

            HttpTunnel.doHttpTunneling(out,dbRecords); dbRecords="";
        }



//

        System.out.println("Value.	"+str);



    }

catch(Exception ex){ ex.printStackTrace(); System.out.println(ex);

    }

}



    private Connection getConnection(){ try{
        Class.forName("com.mysql.jdbc.Driver"); con =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/crime", "root", "admin");
// return con;

    }catch(Exception ex){ ex.printStackTrace(); System.out.println(ex);
    }

        return con;

    }



    public static void main(String[] args) { try{
        MainServer ms = new MainServer(); while(true){
            socket = serverSocket.accept();

            System.out.println("connection accepted	");

            Thread thread = new Thread(ms); thread.start();
        }

    }catch(Exception ex){ ex.printStackTrace(); System.out.println(ex);
    }

    }

}

