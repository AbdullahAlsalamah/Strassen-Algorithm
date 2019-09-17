import java.util.*;
import java.io.*;

public class Proj {
	public static void main(String[] args) throws Exception {
		// Reading the input from text file with fomrat (MatrixMultiply {put type here 0
		// for classical 1 for Strassen} {size of matrix}
		// {Base} {name of the file will have the input} {name of the file will have the
		// output})
		// e.g. matrixMultiply 1 52 13 input3.txt output3.txt
		Scanner br = new Scanner(new FileInputStream("tests.txt"));

		String Case = br.nextLine();

		while (!Case.equals("") && Case != null) {

			Scanner kb = new Scanner(Case);

			kb.next();
			int Type = kb.nextInt();
			double inputSize = kb.nextInt();
			int ModifiedInputSize;
			int Base = kb.nextInt();

			// Modify the input size to become power of two
			if (Math.log(inputSize) != 0)
				ModifiedInputSize = (int) Math.pow(2, Math.ceil(Math.log(inputSize) / Math.log(2)));
			else
				ModifiedInputSize = (int) inputSize;

			String fileNameIn = kb.next();
			String fileNameOut = kb.next();
			// Here file input with random values
			// We send the actual size with the modified input size so if it is not power of
			// two
			// the remaining columns and rows which are >input and <=modified all will be
			// zeros
			fillInputFile(ModifiedInputSize, inputSize, fileNameIn);

			// Initialize two matrices to multiply
			int[][] Matrix1 = new int[ModifiedInputSize][ModifiedInputSize];
			int[][] Matrix2 = new int[ModifiedInputSize][ModifiedInputSize];

			Scanner fileIn = new Scanner(new FileInputStream(fileNameIn));

			// Fill the two matrices
			for (int i = 0; i < ModifiedInputSize; i++) {
				for (int j = 0; j < ModifiedInputSize; j++) {
					Matrix1[i][j] = fileIn.nextInt();
				}
			}

			for (int i = 0; i < ModifiedInputSize; i++) {
				for (int j = 0; j < ModifiedInputSize; j++) {
					Matrix2[i][j] = fileIn.nextInt();
				}
			}

			double startTime = System.nanoTime();
			int[][] OutputMatrix;
			if (Type == 0) {
				OutputMatrix = classical(Matrix1, Matrix2, ModifiedInputSize);
			} else {
				OutputMatrix = Strassen(Matrix1, Matrix2, ModifiedInputSize, Base);
			}
			double endTime = System.nanoTime();

			if (Type == 0)
				System.out.println("For Classical multiplication: ");
			else
				System.out.println("For Strassen multiplication: ");

			System.out.printf("Time taken(t = %d, n = %d, b = %d) = %f\n", Type, (int) inputSize, Base,
					(endTime - startTime) / 1000000000.0);
			System.out.println();

			printM(OutputMatrix, fileNameOut);

			// for trying the second algorithm
			Scanner kb1 = new Scanner(System.in);
			char anotherTest;
			if (Type == 1) {
				System.out.println("Would you like to try Classical algorithm (y/n): ");
				anotherTest = kb1.next().charAt(0);
				if (anotherTest == 'y') {
					System.out.println("For Classical multiplication: ");

					startTime = System.nanoTime();
					if (Type == 0) {
						OutputMatrix = classical(Matrix1, Matrix2, ModifiedInputSize);
					} else {
						OutputMatrix = Strassen(Matrix1, Matrix2, ModifiedInputSize, Base);
					}
					endTime = System.nanoTime();

					System.out.printf("Time taken(t = %d, n = %d, b = %d) = %f\n", Type, (int) inputSize, Base,
							(endTime - startTime) / 1000000000.0);
					System.out.println();
				}

			} else {

				System.out.println("Would you like to try Strassen algorithm (y/n): ");
				anotherTest = kb1.next().charAt(0);
				if (anotherTest == 'y') {
					System.out.println("For Strassen multiplication: ");

					startTime = System.nanoTime();
					if (Type == 0) {
						OutputMatrix = classical(Matrix1, Matrix2, ModifiedInputSize);
					} else {
						OutputMatrix = Strassen(Matrix1, Matrix2, ModifiedInputSize, Base);
					}
					endTime = System.nanoTime();

					System.out.printf("Time taken(t = %d, n = %d, b = %d) = %f\n", Type, (int) inputSize, Base,
							(endTime - startTime) / 1000000000.0);
					System.out.println();

				}
			}

			fileIn.close();

			kb.close();
			if (br.hasNextLine())
				Case = br.nextLine();
			else
				break;
		}
	}

	public static void printM(int[][] C, String outputFile) throws IOException {
		PrintWriter file1 = new PrintWriter(new FileOutputStream(outputFile));

		for (int i = 0; i < C.length; i++) {
			for (int j = 0; j < C.length; j++) {
				file1.print(C[i][j] + " ");
			}
			file1.println();
		}

		file1.close();
	}

	public static int[][] classical(int[][] arr1, int[][] arr2, int n) throws Exception {
		int sum;
		int[][] C = new int[arr1.length][arr1.length];
		for (int i = 0; i < arr1.length; i++) {
			for (int j = 0; j < arr1.length; j++) {
				sum = 0;
				for (int j2 = 0; j2 < arr1.length; j2++) {
					sum += arr1[i][j2] * arr2[j2][j];
				}
				C[i][j] = sum;
			}
		}
		return C;
	}

	public static int[][] MA(int[][] arr1, int[][] arr2) { // Matrix Addition
		int[][] arr3 = new int[arr1.length][arr1.length];
		for (int i = 0; i < arr3.length; i++) {
			for (int j = 0; j < arr3.length; j++) {
				arr3[i][j] = arr1[i][j] + arr2[i][j];
			}
		}
		return arr3;
	}

	public static int[][] MS(int[][] arr1, int[][] arr2) { // Matrix Subtraction
		int[][] arr3 = new int[arr1.length][arr1.length];
		for (int i = 0; i < arr3.length; i++) {
			for (int j = 0; j < arr3.length; j++) {
				arr3[i][j] = arr1[i][j] - arr2[i][j];
			}
		}
		return arr3;
	}

	public static int[][] m(int[][] A, int[][] B) {
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

	public static int[][] Strassen(int[][] arr1, int[][] arr2, int n, int b) {
		if (n <= b) {
			return m(arr1, arr2);
		}

		int[][] A11, A12, A21, A22, B11, B12, B21, B22;
		A11 = new int[n / 2][n / 2];
		A12 = new int[n / 2][n / 2];
		A21 = new int[n / 2][n / 2];
		A22 = new int[n / 2][n / 2];

		B11 = new int[n / 2][n / 2];
		B12 = new int[n / 2][n / 2];
		B21 = new int[n / 2][n / 2];
		B22 = new int[n / 2][n / 2];

		for (int i = 0; i < A11.length; i++) {
			for (int j = 0; j < B22.length; j++) {
				A11[i][j] = arr1[i][j];
				B11[i][j] = arr2[i][j];

				A12[i][j] = arr1[i][j + n / 2];
				B12[i][j] = arr2[i][j + n / 2];

				A21[i][j] = arr1[i + n / 2][j];
				B21[i][j] = arr2[i + n / 2][j];

				A22[i][j] = arr1[i + n / 2][j + n / 2];
				B22[i][j] = arr2[i + n / 2][j + n / 2];
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
		D1 = Strassen(T1, T2, n / 2, b);
		D2 = Strassen(T3, B11, n / 2, b);
		D3 = Strassen(A11, T4, n / 2, b);
		D4 = Strassen(A22, T5, n / 2, b);
		D5 = Strassen(T6, B22, n / 2, b);
		D6 = Strassen(T7, T8, n / 2, b);
		D7 = Strassen(T9, T10, n / 2, b);

		int[][] C11, C12, C21, C22;
		int[][] K1 = MA(D1, D4);
		K1 = MS(K1, D5);
		C11 = MA(K1, D7);

		C12 = MA(D3, D5);
		C21 = MA(D2, D4);

		K1 = MA(D1, D3);
		K1 = MS(K1, D2);
		C22 = MA(K1, D6);

		int[][] C = new int[arr1.length][arr1.length];

		for (int i = 0; i < C11.length; i++) {
			for (int j = 0; j < C11.length; j++) {
				C[i][j] = C11[i][j];
				C[i][j + n / 2] = C12[i][j];
				C[i + n / 2][j] = C21[i][j];
				C[i + n / 2][j + n / 2] = C22[i][j];
			}
		}
		return C;
	}

	public static void fillInputFile(int n, double input, String fileName) throws IOException {
		PrintWriter file1 = new PrintWriter(new FileOutputStream(fileName));
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i >= input || j >= input)
					file1.print(0 + " ");
				else
					file1.print(((int) Math.pow(-1, rand.nextInt(2))) * rand.nextInt(101) + " ");
			}
			file1.println();
		}
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i >= input || j >= input)
					file1.print(0 + " ");
				else
					file1.print(((int) Math.pow(-1, rand.nextInt(2))) * rand.nextInt(101) + " ");
			}
			file1.println();
		}
		file1.close();
	}

}
