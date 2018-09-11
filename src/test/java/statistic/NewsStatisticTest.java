package statistic;

import java.util.ArrayList;

import org.junit.Test;

import dao.NewsDao;
import dao.impl.NewsDaoImpl;
import javabean.Row;

public class NewsStatisticTest {

	@Test
	public void testGetLastestNumber() {
		NewsDao newsDao = new NewsDaoImpl();
		/*
		 * 获得近期一段时间内，每个发布员 发布的新闻的数量
		 */
		String interval = "30 day";
		ArrayList<Row> rows = newsDao.getLastestNumber(interval);
		for(int i=0;i<rows.size();++i) {
			Row row = rows.get(i);
			System.out.println(row.getUserName() + "----" + row.getNumber());
		}
	}
}
