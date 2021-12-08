import api.DirectedWeightedGraphAlgorithms;
import api.DirectedWeightedGraph;
import api.GeoLocation;
import api.NodeData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    public static void main(String[] args)
    {
        String json_file = "G1.json";
        runGUI(json_file);
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraph graph;
        try {
            graph = readJson(json_file);
        } catch (Exception E) {
            E.printStackTrace();
            graph = new Graph();
        }
        return graph;
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms Algo = new Algo();
        DirectedWeightedGraph graph = getGrapg(json_file);
        Algo.init(graph);
        return Algo;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
//        new
    }
    //https://www.youtube.com/watch?v=cFCgFlqF5kw
    public static Graph readJson(String json_file)
            throws FileNotFoundException, IOException, ParseException {
        Graph ans = new Graph();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(json_file));
        JSONObject jsonobj = (JSONObject) obj;
        JSONArray edges = (JSONArray) jsonobj.get("Edges");
        JSONArray nodes = (JSONArray) jsonobj.get("Nodes");
        for (Object o : nodes) {
            JSONObject temp = (JSONObject) o;
            String[] arrOfStr = temp.get("pos").toString().split(",");
            GeoLocation g = new location(Double.parseDouble(arrOfStr[0]),Double.parseDouble(arrOfStr[1]),Double.parseDouble(arrOfStr[2]));
            NodeData n = new Node(Integer.parseInt(temp.get("id").toString()),g);
            ans.addNode(n);
        }
        for (Object o : edges) {
            JSONObject temp = (JSONObject) o;
            if ((temp.get("src") != null) && temp.get("dest") != null && temp.get("w") != null) {
                int src = Integer.parseInt(temp.get("src").toString());
                int dest = Integer.parseInt(temp.get("dest").toString());
                double w = Double.parseDouble(temp.get("w").toString());
                ans.connect(src, dest, w);
            }
        }
        return ans;
    }
}