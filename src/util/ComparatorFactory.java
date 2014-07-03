package util;

import java.util.Comparator;

public class ComparatorFactory {
	private static Comparator _nonSenseStrComp = null;
	
	
	
	public static Comparator getNonSenseStringComparator() {
        if (_nonSenseStrComp == null) {
            _nonSenseStrComp = new ComparatorStringNonSens();
        }
        return _nonSenseStrComp;
    }
    
}
