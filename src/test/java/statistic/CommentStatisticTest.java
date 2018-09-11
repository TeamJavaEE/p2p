package statistic;


import java.util.ArrayList;

import org.junit.Test;

import dao.CommentDao;
import dao.impl.CommentDaoImpl;
import javabean.Row;

public class CommentStatisticTest {

	@Test
	public void testGetLastestNumber() {
		CommentDao commentDao = new CommentDaoImpl();
		/**
		 * 获得一段时间的评论数据
		 */
		String interval = "20 day";
		ArrayList<Row> rows = commentDao.getLastestNumber(interval);
		for(int i=0;i<rows.size();++i) {
			Row row = rows.get(i);
			System.out.println(row.getUserName() + "----" + row.getNumber());
		}
	}

}
