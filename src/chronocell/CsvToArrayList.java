/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;


import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
 
/**
 * @author Crunchify.com
 * 
 */
 
public class CsvToArrayList {
    
    public static double[][] readTXTFile(String csvFileName) throws IOException {
        String line = null;
        BufferedReader stream = null;
        List<List<String>> csvData = new ArrayList<List<String>>();

        try {
            stream = new BufferedReader(new FileReader(csvFileName));
            while ((line = stream.readLine()) != null) {
                String[] splitted = line.split(",");
                List<String> dataLine = new ArrayList<String>(splitted.length);
                for (String data : splitted)
                    dataLine.add(data);
                csvData.add(dataLine);
            }
        } catch (IOException e) {
                e.printStackTrace();
        } finally {
            if (stream != null)
                stream.close();
        }
        int l=csvData.size();
        int c=csvData.get(0).size();
        double[][] array = new double[l][c];
        for (int i=0;i<l;i++){
            for (int j=0;j<c;j++){
                array[i][j]=Double.parseDouble(csvData.get(i).get(j));
            }
        }
        return array;
        }
    
    
    
//	public static void readArray(int l, int c, double[][] array,String file) {		
//		BufferedReader crunchifyBuffer = null;
//		
//		try {
//		//String crunchifyLine;
//            crunchifyBuffer = new BufferedReader(new FileReader(file));
//
//            for (int i=0;i<l;i++){
//                ArrayList<String> temp= csvToArrayList(crunchifyBuffer.readLine());
//                for (int j=0;j<c;j++){
//                    array[i][j]=Double.parseDouble(temp.get(j));
//                    System.out.println(array[i][j]);
//                }
//            }
//
//            } catch (IOException e) {
//                    e.printStackTrace();
//            } finally {
//                try {
//                    if (crunchifyBuffer != null) crunchifyBuffer.close();
//                } catch (IOException crunchifyException) {
//                        crunchifyException.printStackTrace();
//                }
//            }
//        }
	
//	// Utility which converts CSV to ArrayList using Split Operation
//	public static ArrayList<String> csvToArrayList(String crunchifyCSV) {
//		ArrayList<String> crunchifyResult = new ArrayList<String>();
//		
//		if (crunchifyCSV != null) {
//			String[] splitData = crunchifyCSV.split("\\s*,\\s*");
//			for (int i = 0; i < splitData.length; i++) {
//				if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
//					crunchifyResult.add(splitData[i].trim());
//				}
//			}
//		}
//		
//		return crunchifyResult;
//	}
	
}
