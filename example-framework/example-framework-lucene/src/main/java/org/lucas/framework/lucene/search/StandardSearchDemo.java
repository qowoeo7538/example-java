package org.lucas.framework.lucene.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StandardSearchDemo {

    /**
     * 创建索引
     *
     * @throws Exception
     */
    @Test
    public void demoCreateIndex() throws Exception {
        //创建StandardAnalyzer
        Analyzer analyzer = new StandardAnalyzer();
        // 把索引存在硬盘上的一个目录中
        Path path = Paths.get("d:/news");
        //存放新闻的索引
        Directory directory = FSDirectory.open(path);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //IndexWriter写索引
        IndexWriter iwriter = new IndexWriter(directory, config);
        Document doc = new Document();
        //要索引的文本
        String text = "This is the text to be indexed.";
        doc.add(new Field("title", text, TextField.TYPE_STORED));
        //增加文档
        iwriter.addDocument(doc);
        iwriter.close();
        directory.close();
    }

    /**
     *
     */
    public void demoSearchIndex() throws Exception {
        String defaultField = "title";
        String queryString = "test";
        Analyzer analyzer = new StandardAnalyzer();
        // 用于解析查询语法
        QueryBuilder builder = new QueryBuilder(analyzer);
        // 从字符串得到查询对象
        Query query = builder.createBooleanQuery(defaultField, queryString);
        // 存放新闻的索引路径
        Path path = Paths.get("d:/news");
        // 把索引存在硬盘上的一个目录中
        Directory directory = FSDirectory.open(path);
        // DirectoryReader读入一个目录下的索引文件
        IndexReader ir = DirectoryReader.open(directory);

        // 打开索引库
        IndexSearcher searcher = new IndexSearcher(ir);

        // 根据查询词搜索索引库
        // 最多返回10个结果
        TopDocs hits = searcher.search(query, 10);
        System.out.println("hits.totalHits:" + hits.totalHits);
        for (int j = 0; j < hits.scoreDocs.length; j++) {
            // 根据文档编号取出文档对象
            Document hitDoc = searcher.doc(hits.scoreDocs[j].doc);
            // 输出文档
            System.out.println(hitDoc.get("title"));
        }
    }

}
