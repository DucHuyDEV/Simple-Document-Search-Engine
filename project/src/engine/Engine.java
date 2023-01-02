package project.src.engine;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Engine {
    public ArrayList<String> filePaths;
    public int loadDocs(String dirname) {
        int count = 0;
        filePaths = new ArrayList<>();
        File folder = new File(dirname);
        File[] fileList = folder.listFiles();
        if (fileList != null) {
            for (File fileEntry : fileList) {
                if (fileEntry.isDirectory()) {
                    loadDocs(fileEntry.getAbsolutePath());
                } else {
                    if (fileEntry.getName().contains(".DS_Store")) continue;
                    filePaths.add(fileEntry.getAbsolutePath());
                    count++;
                }
            }
        }
        return count;
    }
    public Doc[] getDocs() {
        Doc[] docs = new Doc[filePaths.size()];
        int count = 0;

        for (String filePath : filePaths) {
            StringBuilder lines = new StringBuilder();
            try {
               for (int i = 0; i < 2; i++) {
                   lines.append(Files.readAllLines(Paths.get(filePath)).get(i)).append("\n");
               }
                Doc doc = new Doc(lines.toString());
                docs[count] = doc;
                count++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return docs;
    }

    public List<Result> search(Query q) {
        List<Result> results = new ArrayList<>();
        Result result;
        for (Doc doc : getDocs()) {
            for (Word keyword : q.getKeywords()) {
                result = new Result(doc, q.matchAgainst(doc));
                if (doc.toString().toLowerCase().contains(keyword.getText().toLowerCase())) {
                    results.add(result);
                    break;
                }
            }
        }
        results.sort(Result::compareTo);
        Collections.reverse(results);
        return results;
    }

    public String htmlResult(List<Result> results) {
        results.sort(Result::compareTo);
        Collections.reverse(results);
        StringBuilder html = new StringBuilder();
        for (Result r : results) {
            html.append(r.htmlHighlight());
        }
        return html.toString();
    }
}
