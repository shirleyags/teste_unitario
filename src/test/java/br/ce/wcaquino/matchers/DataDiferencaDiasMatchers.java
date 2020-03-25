package br.ce.wcaquino.matchers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

public class DataDiferencaDiasMatchers extends TypeSafeMatcher<Date> {

	private Integer qtsDias;
	
	

	public DataDiferencaDiasMatchers(Integer qtsDias) {
		this.qtsDias = qtsDias;
	}

	public void describeTo(Description description) {
		Date dataEsperada =  DataUtils.obterDataComDiferencaDias(qtsDias);
		DateFormat format = new SimpleDateFormat("dd/MM/YYYY");
		description.appendText(format.format(dataEsperada));
	}
	

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(qtsDias));
	}

}
