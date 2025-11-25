package com;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProductManagement 
{
	public static void main(String[] args) throws ClassNotFoundException, SQLException 
    {
       
        Connection conn = DbConnection.getConnection(); 
        Scanner sc = new Scanner(System.in);

        int ch = -1; 
        
        do {
            System.out.println("\n=======================================================");
            System.out.println("                 PRODUCT MANAGEMENT SYSTEM");
            System.out.println("=======================================================\n\n");

            System.out.println("  1. ⭐ Display Products By Brand");
            System.out.println("  2. ➕ Insert New Product");
            System.out.println("  3. 🗑️  Delete Product By Code");
            System.out.println("  4. ✏️  Update Product Details");
            System.out.println("  5. 🔍 Display All Products");
            System.out.println("  0. 🛑 Exit Application");
            
            System.out.println("-------------------------------------------------------");
            System.out.print("  ▶️ Enter Your Choice (0-5): ");
            
            if (!sc.hasNextInt()) {
                System.out.println("\n❌ Invalid input! Please enter a number.");
                sc.nextLine(); 
                continue;
            }
            ch = sc.nextInt();
            sc.nextLine(); 
            
            System.out.println("\n=======================================================");
            System.out.println("                 OPERATION OUTPUT");
            System.out.println("=======================================================\n\n");

            try {
                switch(ch) 
                {
                    case 1:
                        PreparedStatement ps = conn.prepareStatement("SELECT product_code, product_name, product_brand, product_price FROM Products WHERE product_brand = ?");
                        
                        System.out.print("Enter Brand Name to search: ");
                        String brand = sc.nextLine();
                        
                        ps.setString(1, brand);
                        ResultSet rs = ps.executeQuery();
                        
                        System.out.printf("| %-6s | %-20s | %-15s | %-10s |\n", "CODE", "NAME", "BRAND", "PRICE");
                        System.out.println("------------------------------------------------------------------");
                        
                        boolean found = false;
                        while(rs.next()) {
                            found = true;
                            // Using getDouble(4) for accuracy
                            System.out.printf("| %-6d | %-20s | %-15s | $%-9.2f |\n", 
                                rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4));
                        }
                        
                        if (!found) {
                            System.out.println("No products found for brand: " + brand);
                        }
                        rs.close();
                        ps.close();
                        break;

                    case 2:
                        PreparedStatement ps1 = conn.prepareStatement("INSERT INTO products (product_name, product_brand, product_price) VALUES (?, ?, ?)");
                        
                        System.out.print("Enter Product Name: ");
                        String pname = sc.nextLine();
                        
                        System.out.print("Enter Product Brand: ");
                        String pbrand = sc.nextLine();
                        
                        System.out.print("Enter Product Price: ");
                        double pr = sc.nextDouble(); 
                        sc.nextLine();
                        
                        ps1.setString(1, pname);
                        ps1.setString(2, pbrand);
                        ps1.setDouble(3, pr);
                        
                        int i = ps1.executeUpdate();
                        
                        if (i > 0) {
                            System.out.println("✅ " + i + " Record Inserted Successfully!");
                        } else {
                            System.out.println("❌ No Record Inserted.");
                        }
                        ps1.close();
                        break;

                    case 3:
                        PreparedStatement ps2 = conn.prepareStatement("DELETE FROM Products WHERE product_code = ?");
                        
                        System.out.print("Enter Product Code to delete: ");
                        int pc = sc.nextInt();
                        sc.nextLine(); 
                        
                        ps2.setInt(1, pc);
                        int ie = ps2.executeUpdate();
                        
                        if (ie > 0) {
                            System.out.println("✅ " + ie + " Record Deleted Successfully!");
                        } else {
                            System.out.println("⚠️ No Record Deleted. Product Code not found.");
                        }
                        ps2.close();
                        break;
                        
                    case 4:
                        System.out.println("\n  1. Update Product Name");
                        System.out.println("  2. Update Product Brand");
                        System.out.println("  3. Update Product Price");
                        System.out.print("  Choose field to update: ");
                        
                        if (!sc.hasNextInt()) {
                            System.out.println("\n❌ Invalid choice.");
                            sc.nextLine(); 
                            break;
                        }
                        int ch1 = sc.nextInt();
                        sc.nextLine(); 
                        
                        int updateCode;
                        
                        switch (ch1) {
                            case 1:
                                PreparedStatement ps3 = conn.prepareStatement("UPDATE products SET product_name = ? WHERE product_code = ?");
                                System.out.print("Enter New Product Name: ");
                                String pname1 = sc.nextLine();
                                System.out.print("Enter Product Code to update: ");
                                updateCode = sc.nextInt();
                                sc.nextLine(); 
                                
                                ps3.setString(1, pname1);
                                ps3.setInt(2, updateCode);
                                
                                int i2 = ps3.executeUpdate();
                                if (i2 > 0) System.out.println("✅ " + i2 + " Name Updated Successfully!");
                                else System.out.println("⚠️ Update failed. Code not found.");
                                ps3.close();
                                break;

                            case 2:
                                PreparedStatement ps4 = conn.prepareStatement("UPDATE products SET product_brand = ? WHERE product_code = ?");
                                System.out.print("Enter New Brand Name: ");
                                String brand2 = sc.nextLine();
                                System.out.print("Enter Product Code to update: ");
                                updateCode = sc.nextInt();
                                sc.nextLine(); 
                                
                                ps4.setString(1, brand2);
                                ps4.setInt(2, updateCode);
                                
                                int i3 = ps4.executeUpdate();
                                if (i3 > 0) System.out.println("✅ " + i3 + " Brand Updated Successfully!");
                                else System.out.println("⚠️ Update failed. Code not found.");
                                ps4.close();
                                break;

                            case 3:
                                PreparedStatement ps5 = conn.prepareStatement("UPDATE products SET product_price = ? WHERE product_code = ?");
                                System.out.print("Enter New Product Price: ");
                                double pr1 = sc.nextDouble(); 
                                System.out.print("Enter Product Code to update: ");
                                updateCode = sc.nextInt();
                                sc.nextLine(); // Clear buffer
                                
                                ps5.setDouble(1, pr1);
                                ps5.setInt(2, updateCode);
                                
                                int i4 = ps5.executeUpdate();
                                if (i4 > 0) System.out.println("✅ " + i4 + " Price Updated Successfully!");
                                else System.out.println("⚠️ Update failed. Code not found.");
                                ps5.close();
                                break;

                            default:
                                System.out.println("❌ Invalid Update Choice.");
                        }
                        break;

                    case 5:
                        PreparedStatement ps6 = conn.prepareStatement("SELECT product_code, product_name, product_brand, product_price FROM Products");
                        ResultSet rs6 = ps6.executeQuery();
                        
                        System.out.printf("| %-6s | %-20s | %-15s | %-10s |\n", "CODE", "NAME", "BRAND", "PRICE");
                        System.out.println("------------------------------------------------------------------");
                        
                        while(rs6.next()) {
                            // Using getDouble(4) for accuracy
                            System.out.printf("| %-6d | %-20s | %-15s | $%-9.2f |\n", 
                                rs6.getInt(1), rs6.getString(2), rs6.getString(3), rs6.getDouble(4));
                        }
                        rs6.close();
                        ps6.close();
                        break;
                        
                    case 0:
                        System.out.println("\n👋 Thank you for using the Product Management System. Goodbye!");
                        break;

                    default:
                        System.out.println("\n❌ Invalid choice. Please select an option between 0 and 5.");
                }
            } catch (SQLException e) {
                System.out.println("\n🚨 Database Error occurred: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n🚨 An unexpected error occurred: " + e.getMessage());
            }

            
            if (ch != 0) {
                 System.out.print("\nPress Enter to continue to the main menu...");
                 sc.nextLine();
            }

        } while (ch != 0); // Loop continues until user chooses 0

        sc.close();
        conn.close();
    }
}

