package api.utilities;
import org.testng.annotations.DataProvider;
import java.io.IOException;

public class DataProviders {
	
	
	@DataProvider(name = "getAllData")
    public Object[][] getAllData() throws IOException {
        // Path to the Excel file and the sheet name
        String xlfile = "C:\\RestAssured_tool\\Workspace_CM\\RestAssured_CM\\testData_DDT\\sheet1.xlsx";  // Replace with the actual path
        String xlsheet = "sheet1"; 																		  // Replace with your sheet name
        // Get the row count and column count
        int rowCount = XLUtils.getRowCount(xlfile, xlsheet);
        int colCount = XLUtils.getCellCount(xlfile, xlsheet, 0);  // Column count of the first row
        System.out.println("Row count: " + rowCount);
        System.out.println("Column count: " + colCount);
        
        // Create a 2D array to hold the Excel data
        Object[][] data = new Object[rowCount][colCount];
        
        
        // Loop through each row and column to fetch the data
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                // Get cell data and store it in the 2D array
                String cellData = XLUtils.getCellData(xlfile, xlsheet, i + 1, j); // +1 for skipping header row
                // Check if the cell is empty and handle accordingly
                if (cellData == null || cellData.trim().isEmpty()) {
                    data[i][j] = getDefaultValue(j);  // Get a default value based on the column index
                } else {
                    // Convert string data to the appropriate type based on the column index
                    if (j == 5 || j == 6 || j == 10) { // Assuming columns 5, 6, and 10 are integers
                        try {
                            data[i][j] = Integer.parseInt(cellData);  // Convert to int
                        } catch (NumberFormatException e) {
                            data[i][j] = 0;  // Assign default value if parsing fails
                        }
                    } else if (j == 11 || j == 12 || j == 13) { // Assuming columns 11, 12, and 13 are booleans
                        data[i][j] = Boolean.parseBoolean(cellData);  // Convert to boolean
                    } else {
                        data[i][j] = cellData;  // Default to String
                    }
                }
            }
        }
        
        return data;
    }
	
	// Helper method to provide default values for empty cells based on the column index
	private Object getDefaultValue(int colIndex) {
	    if (colIndex == 5 || colIndex == 6 || colIndex == 10) {
	        return 0;  // Default value for integer columns
	    } else if (colIndex == 11 || colIndex == 12 || colIndex == 13) {
	        return false;  // Default value for boolean columns
	    } else {
	        return "";  // Default value for String columns
	    }
	}

}