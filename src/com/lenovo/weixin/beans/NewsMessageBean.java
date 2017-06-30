package com.lenovo.weixin.beans;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class NewsMessageBean extends MessageBeanBase {
	private int ArticleCount;
	private List<ItemBean> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<ItemBean> getArticles() {
		return Articles;
	}

	public void setArticles(List<ItemBean> articles) {
		Articles = articles;
	}
}
