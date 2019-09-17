
public class test {
	public static void main(String[] args) {
		int[][] A = {{7,9,8,6},{7,4,6,7},{3,0,9,6},{3,2,6,7}};
		int[][] B = {{9,7,3,6},{8,7,4,9},{5,3,0,1},{6,8,9,5}};
		
		//int[][] A4 = {{3,7},{3,2}};
		//int[][] B4 = {{8,9},{2,5}};
		int[][] C = m(A, B);
		System.out.println("Classical :");
		printM(C);
		System.out.println();
		int[][] A1 = {{7,9,8,6},{7,4,6,7},{3,0,9,6},{3,2,6,7}};
		int[][] B1 = {{9,7,3,6},{8,7,4,9},{5,3,0,1},{6,8,9,5}};
		int[][] L = Strassen(A, B, A.length);
		System.out.println("Strassen : ");
		printM(L);
		
	}
	
	
	public static void printM(int[][] C) {
		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C.length; j++) {
				System.out.print(C[i][j] + " ") ;
			}
			System.out.println();
		}
	}
	
	public static int[][] m(int[][] A, int[][] B){
		int[][] C = new int[A.length][B.length];
		for (int i = 0; i < B.length; i++) {
			for (int j = 0; j < B.length; j++) {
				int sum = 0;
				for (int j2 = 0; j2 < B.length; j2++) {
					sum += A[i][j2] * B[j2][j];
				}
				C[i][j] = sum;
			}
		}
		return C;
	}
	
	public static int[][] MA(int[][] arr1, int[][] arr2) {		//Matrix Addition
		int[][] arr3 = new int[arr1.length][arr1.length];
		for (int i = 0; i < arr3.length; i++) {
			for (int j = 0; j < arr3.length; j++) {
				arr3[i][j] = arr1[i][j] + arr2[i][j];
			}
		}
		return arr3;
 	}
	
	public static int[][] MS(int[][] arr1, int[][] arr2) {		//Matrix Subtraction 
		int[][] arr3 = new int[arr1.length][arr1.length];
		for (int i = 0; i < arr3.length; i++) {
			for (int j = 0; j < arr3.length; j++) {
				arr3[i][j] = arr1[i][j] - arr2[i][j];
			}
		}
		return arr3;
 	}
	
	public static int[][] Strassen(int[][] arr1, int[][] arr2, int n) {
		if(n == 1) {
			/*int[][] C = new int[n][n];
			C[0][0] = arr1[0][0] * arr2[0][0];
			return C;*/
			return m(arr1, arr2);
		} 
		
		int[][] A11, A12, A21, A22, B11, B12, B21, B22;
		A11 = new int[n/2][n/2];
		A12 = new int[n/2][n/2];
		A21 = new int[n/2][n/2];
		A22 = new int[n/2][n/2];
		
		B11 = new int[n/2][n/2];
		B12 = new int[n/2][n/2];
		B21 = new int[n/2][n/2];
		B22 = new int[n/2][n/2];
		
		for (int i = 0; i < A11.length; i++) {
			for (int j = 0; j < B22.length; j++) {
				A11[i][j] = arr1[i][j];
				B11[i][j] = arr2[i][j];
				
				A12[i][j] = arr1[i][j + n/2];
				B12[i][j] = arr2[i][j + n/2];
				
				A21[i][j] = arr1[i + n/2][j];
				B21[i][j] = arr2[i + n/2][j];
				
				A22[i][j] = arr1[i + n/2][j + n/2];
				B22[i][j] = arr2[i + n/2][j + n/2];
			}
		}
		
		
		
		int[][] T1, T2, T3, T4, T5, T6, T7, T8, T9, T10;
		T1 = MA(A11, A22);
		T2 = MA(B11, B22);
		T3 = MA(A21, A22);
		T4 = MS(B12, B22);
		T5 = MS(B21, B11);
		T6 = MA(A11, A12);
		T7 = MS(A21, A11);
		T8 = MA(B11, B12);
		T9 = MS(A12, A22);
		T10 = MA(B21, B22);
		
		int[][] D1, D2, D3, D4, D5, D6, D7;
		D1 = Strassen(T1, T2, n/2);
		D2 = Strassen(T3, B11, n/2);
		D3 = Strassen(A11, T4, n/2);
		D4 = Strassen(A22, T5, n/2);
		D5 = Strassen(T6, B22, n/2);
		D6 = Strassen(T7, T8, n/2);
		D7 = Strassen(T9, T10, n/2);
		
		int[][] C11, C12, C21, C22;
		/*int[][] K1 = MA(D1, D4);
		K1 = MS(K1, D5);
		C11 = MA(K1,D7);*/
		
		C11 = MA(MA(D1,MS(D4,D5)),D7);
		C12 = MA(D3,D5);
		C21 = MA(D2,D4);
		C22 = MA(MA(D1,MS(D3,D2)), D6);
		
		/*K1 = MA(D1, D3);
		K1 = MS(K1, D2);
		C22 = MA(K1,D6);*/
		
		
		
		int[][] C = new int[arr1.length][arr1.length];
		
		for (int i = 0; i < C11.length; i++) {
			for (int j = 0; j < C11.length; j++) {
				C[i][j] = C11[i][j];
				C[i][j+n/2] = C12[i][j];
				C[i +n/2][j] = C21[i][j];
				C[i+n/2][j+n/2] = C22[i][j];
			}
		}
		return C;
	}
	
}
