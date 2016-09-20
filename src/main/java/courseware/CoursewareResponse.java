package courseware;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;


/**
 * Created by ABM on 8/25/2016.
 */
public class CoursewareResponse {
    static ArrayList<Course> courses = new ArrayList<Course>();
    static ArrayList<String> TAs = new ArrayList<String>();

    public  void  coursewareResponseFunc() throws JSONException {
        parseCourses(getJSONFromUrl("https://courseware.sbu.ac.ir/api/v1/courses?state=available&access_token=xeJqvh0OXXOvzZNNUsy53R2CV5Vpr7GVN2rZB9s7ueyUz4Y2S5X1X2RU3cEcEvmK"));
        for (Course c: courses){
            parseTAs(getJSONFromUrl("https://courseware.sbu.ac.ir/api/v1/courses/"+c.id+"/users?enrollment_type=ta&access_token=xeJqvh0OXXOvzZNNUsy53R2CV5Vpr7GVN2rZB9s7ueyUz4Y2S5X1X2RU3cEcEvmK"));
            System.out.println("------------------------------------------------");
        }
    }

    public static JSONArray getJSONFromUrl(String url) {
        InputStream is = null;
        JSONArray jArray = null;
        String json ="";


        // Making HTTP request
        try {
            // defaultHttpClient

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            System.out.println("Buffer Error Error converting result ");
        }

        // try parse the string to a JSON object
        try {
            json = clear(json);
            System.out.println(json);
            jArray = new JSONArray(json);
        } catch (JSONException e) {
            System.out.println("JSON Parser Error parsing data ");
        }


        return jArray;

    }
    public static void parseCourses(JSONArray response) throws JSONException {

        for (int i = 0; i < response.length(); i++) {
            JSONObject jsonobject = response.getJSONObject(i);
            String year = jsonobject.getString("start_at").split("T")[0].split("-")[0];

            if (Integer.parseInt(year) >= 2016) {
                String courseName = jsonobject.getString("course_code");
                Integer id = jsonobject.getInt("id");
                System.out.println(courseName);
                courses.add(new Course(courseName , id));

            }
        }
    }



    public static void parseTAs(JSONArray response) throws JSONException {

        for (int i = 0; i < response.length(); i++) {
            JSONObject jsonobject = response.getJSONObject(i);
                String TAName = jsonobject.getString("name");
                System.out.println(TAName);
                TAs.add(TAName);
        }
    }




    public static String clear(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length()-1)=='n') {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }
}
