package com.fatec.teste;

import static org.junit.Assert.*;

import org.junit.Test;

import org.junit.Before;
import org.junit.After;

import static org.hamcrest.CoreMatchers.is;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

import java.util.HashMap;

import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;

public class UC01ConsultaCEP {
	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver.exe");
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void CT01ConsultaComSucesso() {
		driver.get("http://www.buscacep.correios.com.br/sistemas/buscacep/buscaCep.cfm");
		{
			WebElement dropdown = driver.findElement(By.name("UF"));
			dropdown.findElement(By.xpath("//option[. = 'SP']")).click();
		}
		driver.findElement(By.name("Localidade")).sendKeys("São Paulo");
		{
			WebElement dropdown = driver.findElement(By.name("Tipo"));
			dropdown.findElement(By.xpath("//option[. = 'Rua']")).click();
		}
		driver.findElement(By.name("Tipo")).click();
		driver.findElement(By.name("Logradouro")).sendKeys("Taquari");
		driver.findElement(By.cssSelector(".btn2")).click();
		espera();
		assertThat(driver.findElement(By.cssSelector("p")).getText(), is("DADOS ENCONTRADOS COM SUCESSO."));
	}

	public void espera() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void CT02ConsultaCEPComLogradouroInvalido() {
		driver.get("http://www.buscacep.correios.com.br/sistemas/buscacep/buscaCep.cfm");
		{
			WebElement dropdown = driver.findElement(By.name("UF"));
			dropdown.findElement(By.xpath("//option[. = 'SP']")).click();
		}
		driver.findElement(By.name("Localidade")).sendKeys("São Paulo");
		{
			WebElement dropdown = driver.findElement(By.name("Tipo"));
			dropdown.findElement(By.xpath("//option[. = 'Rua']")).click();
		}
		driver.findElement(By.name("Tipo")).click();
		driver.findElement(By.name("Logradouro")).sendKeys("xxxxxx");
		driver.findElement(By.cssSelector(".btn2")).click();
		espera();
		assertThat(driver.findElement(By.cssSelector("p")).getText(), is("LOGRADOURO NAO ENCONTRADO."));
	}

	@Test
	public void CT03ConsultaCEPComLogradouroeEmBranco() {
		driver.get("http://www.buscacep.correios.com.br/sistemas/buscacep/buscaCep.cfm");
		{
			WebElement dropdown = driver.findElement(By.name("UF"));
			dropdown.findElement(By.xpath("//option[. = 'SP']")).click();
		}
		driver.findElement(By.name("Localidade")).sendKeys("São Paulo");
		{
			WebElement dropdown = driver.findElement(By.name("Tipo"));
			dropdown.findElement(By.xpath("//option[. = 'Rua']")).click();
		}
		driver.findElement(By.name("Tipo")).click();
		driver.findElement(By.name("Logradouro")).sendKeys("");
		driver.findElement(By.cssSelector(".btn2")).click();
		espera();
		assertThat(driver.switchTo().alert().getText(), is("Informe o logradouro !"));
	}

	@Test
	public void CT04ConsultaCEPComLogradouroInvalido() {
		driver.get("http://www.buscacep.correios.com.br/sistemas/buscacep/buscaCep.cfm");
		{
			WebElement dropdown = driver.findElement(By.name("UF"));
			dropdown.findElement(By.xpath("//option[. = 'SP']")).click();
		}
		driver.findElement(By.name("Localidade")).sendKeys("São Paulo");
		{
			WebElement dropdown = driver.findElement(By.name("Tipo"));
			dropdown.findElement(By.xpath("//option[. = 'Rua']")).click();
		}
		driver.findElement(By.name("Tipo")).click();
		driver.findElement(By.name("Logradouro")).sendKeys("@%@%");
		driver.findElement(By.cssSelector(".btn2")).click();
		espera();
		//assertThat(driver.findElement(By.cssSelector("p")).getText(), is("LOGRADOURO NAO INFORMADO/INVALIDO."));
		assertThat(driver.getTitle(), is("resultadoBuscaCep"));
		
	}

}
