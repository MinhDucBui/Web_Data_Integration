package WDI.Comparators;

import WDI.model.Book;
import de.uni_mannheim.informatik.dws.winter.matching.rules.Comparator;
import de.uni_mannheim.informatik.dws.winter.matching.rules.ComparatorLogger;
import de.uni_mannheim.informatik.dws.winter.model.Correspondence;
import de.uni_mannheim.informatik.dws.winter.model.Matchable;
import de.uni_mannheim.informatik.dws.winter.model.defaultmodel.Attribute;
//import de.uni_mannheim.informatik.dws.winter.similarity.string.GeneralisedStringJaccard;
//import de.uni_mannheim.informatik.dws.winter.similarity.string.JaccardOnNGramsSimilarity;
import de.uni_mannheim.informatik.dws.winter.similarity.string.TokenizingJaccardSimilarity;

/**
 * {@link Comparator} for {@link Movie}s based on the {@link Movie#getTitle()}
 * value and their {@link TokenizingJaccardSimilarity} value.
 * 
 * @author Robert Meusel (robert@dwslab.de)
 * @author Oliver Lehmberg (oli@dwslab.de)
 * 
 */
public class BookTitleComparatorJaccard implements Comparator<Book, Attribute> {

	private static final long serialVersionUID = 1L;
	private TokenizingJaccardSimilarity sim = new TokenizingJaccardSimilarity();
	// private JaccardOnNGramsSimilarity sim = new JaccardOnNGramsSimilarity(2);
	// private GeneralisedStringJaccard sim = new GeneralisedStringJaccard(new
	// TokenizingJaccardSimilarity(), 05, 0.5);

	private ComparatorLogger comparisonLog;

	@Override
	public double compare(Book record1, Book record2, Correspondence<Attribute, Matchable> schemaCorrespondences) {

		String s1 = record1.getTitle();
		String s2 = record2.getTitle();

		if (this.comparisonLog != null) {
			this.comparisonLog.setComparatorName(getClass().getName());
			this.comparisonLog.setRecord1Value(s1);
			this.comparisonLog.setRecord2Value(s2);
		}

		if (s1 != null) {
			s1 = preprocesString(s1);
		} else {
			s1 = "";
		}

		if (s2 != null) {
			s2 = preprocesString(s2);
		} else {
			s2 = "";
		}

		// calculate similarity
		double similarity = sim.calculate(s1, s2);

//		// postprocessing
//		int postSimilarity = 0;
//		if (similarity <= 0.3) {
//			postSimilarity = 0;
//		}
//		else postSimilarity = 1;
//
//		postSimilarity *= similarity;
//
//		if (this.comparisonLog != null) {
//			this.comparisonLog.setRecord1PreprocessedValue(s1);
//			this.comparisonLog.setRecord2PreprocessedValue(s2);
//
//			this.comparisonLog.setSimilarity(Double.toString(similarity));
//			this.comparisonLog.setPostprocessedSimilarity(Double.toString(postSimilarity));
//		}

		return similarity;
	}

	public String preprocesString(String s) {
		// Normalize Spelling: lowercase and remove punctuation
		s = s.toLowerCase();
		s = s.replaceAll("\\p{Punct}", "");
		return s;
	}

	@Override
	public ComparatorLogger getComparisonLog() {
		return this.comparisonLog;
	}

	@Override
	public void setComparisonLog(ComparatorLogger comparatorLog) {
		this.comparisonLog = comparatorLog;
	}

}