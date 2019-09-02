package prog;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Crawler {
	static WebDriver driver = null;

	public static void main(String[] args) {
		(new Crawler()).crawlProg(1);
		(new Crawler()).crawlProg(2);
		(new Crawler()).crawlProg(3);
		(new Crawler()).crawlProg(4);
		(new Crawler()).crawlProg(5);
	}

	@SuppressWarnings("unchecked")
	public void crawlProg(int level) {
		WebElement wel = null;
		String url = "https://programmers.co.kr/learn/challenges";
		driver = new Selenium().getDriver();
		try {
			driver.get(url);
			wait(1);
			// 모든 문제
			wel = getElementByQuery("body > div.main > div.challenges__tabs--wrap > ul > li:nth-child(2) > a");
			clicker(wel, 1);
			// Level 1
			wel = getElementByQuery("#collapseFilterLevel > li:nth-child(" + level + ") > label");
			clicker(wel, 1);
			// JAVA
			wel = getElementByQuery("#collapseFilterLanguage > li:nth-child(5) > label");
			clicker(wel, 1);
			// 기본 세팅
			JSONArray array = new JSONArray();
			// 1차
			array = getInfo(array);
			wel = getElementByQuery(
					"#tab_all_challenges > section > div > div > div.algorithm-list > div > nav > ul > li:nth-child(3) > a");
			clicker(wel, 1);
			// 2차
			array = getInfo(array);
			Collections.sort(array, new Comparator<JSONObject>() {
				@Override
				public int compare(JSONObject a, JSONObject b) {
					if (Integer.parseInt((String) a.get("success")) < Integer.parseInt((String) b.get("success"))) {
						return 1;
					} else if (Integer.parseInt((String) a.get("success")) > Integer
							.parseInt((String) b.get("success"))) {
						return -1;
					}
					return 0;
				}
			});
			saveJSON("level" + level, array);
		} finally {
			driver.close();
			driver.quit();
		}
	}

	public void saveJSON(String name, JSONArray array) {
		try {
			FileWriter file = new FileWriter("../assets/json/" + name + ".json");
			file.write(array.toJSONString());
			file.flush();
			file.close();
			System.out.println("저장 성공");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public JSONArray getInfo(JSONArray array) {
		JSONObject obj = null;
		Pattern p = Pattern.compile("(\\d*)명");
		Matcher m = null;
		List<WebElement> welList = getElementsByQuery(
				"#tab_all_challenges > section > div > div > div.algorithm-list > div.row > div > div > a");
		for (WebElement v : welList) {
			obj = new JSONObject();
			obj.put("name", v.findElement(By.className("title")).getText());
			obj.put("url", v.getAttribute("href"));
			obj.put("category", v.findElement(By.className("level")).getText());
			m = p.matcher(v.findElement(By.className("finished-count")).getText());
			m.find();
			obj.put("success", m.group(1));
			System.out.println(obj);
			array.add(obj);
		}
		return array;
	}

	public void wait(int second) {
		for (int i = 0; i < second; i++) {
			System.out.println("...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public WebElement getElementByQuery(String query) {
		return driver.findElement(By.cssSelector(query));
	}

	public List<WebElement> getElementsByQuery(String query) {
		return driver.findElements(By.cssSelector(query));
	}

	public void clicker(WebElement wel, int second) {
		wel.click();
		wait(second);
	}
}