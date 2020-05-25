package org.lucas.example.foundation.web.page;

public class PageTests {

    /*@Test
    public void testPage(){
        //置顶文章
        List<ArticleBO> top = getArticleListWithPool(ArticlePoolTypeEnum.TOP.getCode(), articleLookTypeList);
        //精选文章
        List<ArticleBO> chosen = getArticleListWithPool(ArticlePoolTypeEnum.CHOSEN.getCode(), articleLookTypeList);
        top.addAll(chosen);

        //置顶池和精选池的总数量
        int count = top.size();
        int clientPageSize = discoverProperties.getClientPageSize();
        int startPage = PageUtils.getStartPage(request.getCurrentPage(), clientPageSize);
        int nextPage = request.getCurrentPage() * clientPageSize;

        //数据规则筛选
        if (nextPage <= count) {
            //直接从池中取出
            return top.stream().skip(startPage).limit(clientPageSize).collect(Collectors.toList());
        } else if (startPage <= count) {
            //部分从池中取出，部分从DB取出
            List<ArticleBO> articleList = top.stream().skip(startPage).limit(clientPageSize).collect(Collectors.toList());
            List<ArticleBO> articleBOS = clientArticleBiz.queryArticlePage(0, clientPageSize - articleList.size(), articleLookTypeList);
            articleList.addAll(articleBOS);
            return articleList;
        } else {
            //直接从DB中取出
            return clientArticleBiz.queryArticlePage(startPage - count,clientPageSize, articleLookTypeList);
        }
    }*/

}
