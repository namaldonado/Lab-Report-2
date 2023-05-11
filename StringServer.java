import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class StringHandler implements URLHandler {
  List<String> lines;
  String path;
  StringHandler(String path) throws IOException {
    this.path = path;
    this.lines = Files.readAllLines(Paths.get(path));
  }
  public String handleRequest(URI url) {
    String query = url.getQuery();
    if(url.getPath().equals("/add")) {
      if(query.startsWith("s=")) {
        String toAdd = query.split("=")[1];
        this.lines.add(toAdd);
        return String.format("%s added, there are now %s lines\n", toAdd, this.lines.size());
      }
      else {
        return "/add requires a query parameter s\n";
      }
    }
    else {
      return String.join("\n", lines) + "\n";
    }
  }
}

class StringServer {
  public static void main(String[] args) throws IOException {
    if(args.length == 0){
      System.out.println("Missing port number! Try any number between 1024 to 49151");
      return;
    }

    int port = Integer.parseInt(args[0]);

    Server.start(port, new StringHandler(args[1]));
  }
}
