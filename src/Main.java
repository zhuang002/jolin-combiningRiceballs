import java.util.HashMap;
import java.util.Scanner;

public class Main {

	static HashMap<Parameter, CombineResult> cache = new HashMap<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] seq = new int[n];
		
		// case 1:
		CombineResult result = combine(seq, 0, n-1);
		
		System.out.println(result.maxCombinedAmoumnt);
	}

	private static CombineResult combine(int[] seq, int i, int j) {
		// TODO Auto-generated method stub
		CombineResult result = new CombineResult();
		if (i==j) {
			result.isCombinable = true;
			result.maxCombinedAmoumnt = seq[i];
			return result;
		}
		
		if (i+1==j && seq[i]==seq[j]) {
			result.isCombinable = true;
			result.maxCombinedAmoumnt = seq[i]+seq[j];
			return result;
		}
		
		Parameter para = new Parameter(i,j);
		if (cache.containsKey(para)) {
			return cache.get(para);
		}
		
		//case 1:
		int maxAmount = 0;
		for (int middle=i+1; middle<=j-1; middle++) {
			CombineResult result1 = combine(seq, i,middle);
			CombineResult result2 = combine(seq, middle+1, j);
			if (result1.isCombinable && result2.isCombinable && result1.maxCombinedAmoumnt == result2.maxCombinedAmoumnt) {
				result.isCombinable = true;
				result.maxCombinedAmoumnt = result1.maxCombinedAmoumnt+result2.maxCombinedAmoumnt;
				cache.put(para, result);
				return result;
			} else {
				if (result1.maxCombinedAmoumnt>maxAmount && result1.maxCombinedAmoumnt>result2.maxCombinedAmoumnt)
					maxAmount = result1.maxCombinedAmoumnt;
				if (result2.maxCombinedAmoumnt>maxAmount && result2.maxCombinedAmoumnt>result1.maxCombinedAmoumnt)
					maxAmount = result2.maxCombinedAmoumnt;
			}
		}
		
		//case 2:
		for (int mid1=i+1;mid1<j-2;mid1++) {
			for (int mid2=mid1+1;mid2<j-1;mid2++) {
				CombineResult result1 = combine(seq, i, mid1);
				CombineResult result2 = combine(seq, mid1+1,mid2);
				CombineResult result3 = combine(seq, mid2+1,j);
				
				if (result1.isCombinable && result2.isCombinable && result3.isCombinable &&
						result1.maxCombinedAmoumnt == result2.maxCombinedAmoumnt) {
					result.isCombinable = true;
					result.maxCombinedAmoumnt = result1.maxCombinedAmoumnt+result2.maxCombinedAmoumnt+result3.maxCombinedAmoumnt;
					cache.put(para, result);
					return result;
				}
				else {
					if (maxAmount<result1.maxCombinedAmoumnt) {
						maxAmount=result1.maxCombinedAmoumnt;
					}
					if (maxAmount<result2.maxCombinedAmoumnt) {
						maxAmount=result1.maxCombinedAmoumnt;
					}
					if (maxAmount<result2.maxCombinedAmoumnt) {
						maxAmount=result2.maxCombinedAmoumnt;
					}
				}
			}
		}
		result.isCombinable = false;
		result.maxCombinedAmoumnt = maxAmount;
		
		return result;
	}

}

class CombineResult {
	boolean isCombinable=false;
	int maxCombinedAmoumnt = 0;
}

class Parameter {
	int i;
	int j;
	
	public Parameter(int i, int j) {
		this.i=i;
		this.j=j;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parameter other = (Parameter) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		return true;
	}
	
	
}
