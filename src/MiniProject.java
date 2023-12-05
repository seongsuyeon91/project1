import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class MiniProject
{
	static
	{
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
		}
	}

	static Scanner sc = new Scanner(System.in);

	public static void showMenu1()
	{
		System.out.println("┌───────────────────────────┐");
		System.out.println("│    ▷메뉴를 선택하세요◁    │");
		System.out.println("│===========================│");
		System.out.println("│      1.회  원  관  리     │");
		System.out.println("│      2.대  출  관  리     │");
		System.out.println("│      3.도  서  관  리     │");
		System.out.println("│      0.종          료     │");
		System.out.println("└───────────────────────────┘");

	}

	public static void showMenu2()
	{
		System.out.println("┌───────────────────────────┐");
		System.out.println("│     ▷ 회  원  관  리 ◁    │");
		System.out.println("│===========================│");
		System.out.println("│      1.신규회원 등록      │");
		System.out.println("│      2.회원정보 조회      │");
		System.out.println("│      3.회원     탈퇴      │");
		System.out.println("│      4.회원     경고      │");
		System.out.println("│      5.블랙리스트 조회    │");
		System.out.println("│      6.이전   메뉴로      │");
		System.out.println("└───────────────────────────┘");
	}

	public static void showMenu3()
	{
		System.out.println("┌───────────────────────────┐");
		System.out.println("│     ▷ 대  출  관  리 ◁    │");
		System.out.println("│===========================│");
		System.out.println("│      1.도서    대출       │");
		System.out.println("│      2.도서    반납       │");
		System.out.println("│      3.대출기한연장       │");
		System.out.println("│      4.이전  메뉴로       │");
		System.out.println("└───────────────────────────┘");
	}

	public static void showMenu4()
	{
		System.out.println("┌───────────────────────────┐");
		System.out.println("│     ▷ 도  서  관  리 ◁    │");
		System.out.println("│===========================│");
		System.out.println("│      1.도서     등록      │");
		System.out.println("│      2.도서     검색      │");
		System.out.println("│      3.도서     삭제      │");
		System.out.println("│      4.전체 도서목록      │");
		System.out.println("│      5.이전  메뉴로       │");
		System.out.println("└───────────────────────────┘");
	}

	public static void addMember(Connection con)
	{
		PreparedStatement pstmt1;
		System.out.println("ID : ");
		String ID = sc.nextLine();
		System.out.println("비밀번호 : ");
		String password = sc.nextLine();
		System.out.println("전화번호: ");
		String phoneNumber = sc.nextLine();

		try
		{
			String mInsert = "insert into memberDB values(?,?,?,0, 0)";
			pstmt1 = con.prepareStatement(mInsert);

			pstmt1.setString(1, ID);
			pstmt1.setString(2, password);
			pstmt1.setString(3, phoneNumber);
			int updateCount = pstmt1.executeUpdate();
			System.out.println("회원이 추가 되었습니다. (" + updateCount + ")");
			pstmt1.close();
		} catch (Exception e)
		{
			System.out.println("추가 중 오류가 발생했습니다.\n" + e.getMessage());
		}
	}

	public static void selMember(Connection con)
	{
		PreparedStatement pstmt2;
		System.out.print("조회할 이름 : ");
		String name = sc.nextLine();

		try
		{
			String mSelect = "select * from memberDB where id = ?";
			pstmt2 = con.prepareStatement(mSelect);
			pstmt2.setString(1, name);
			ResultSet rs = pstmt2.executeQuery();
			int count = 0;
			while (rs.next())
			{
				count++;
				String id = rs.getString("ID");
				String phonenumber = rs.getString("phoneNumber");
				String password = rs.getString("PWD");
				String warn = rs.getString("Warning");
				String total = rs.getString("Total");
				
				System.out.println("이름 : " + id);
				System.out.println("번호 : " + phonenumber);
				System.out.println("비밀번호 : " + password);
				System.out.println(warn + "회 경고 받았습니다.");
				System.out.println("("+ total + ") 권 빌렸습니다.");
				
			}
			if (count == 0)
				System.out.println("조회할 데이터가 없습니다.");
			pstmt2.close();
		} catch (Exception e)
		{
			System.out.println("조회 중 오류가 발생했습니다.\n" + e.getMessage());
		}
	}

	public static void delMember(Connection con)
	{
		PreparedStatement pstmt3;
		System.out.print("삭제할 이름 : ");
		String id = sc.nextLine();

		try
		{
			String mDelete = "delete from memberDB where id = ?";
			pstmt3 = con.prepareStatement(mDelete);
			pstmt3.setString(1, id);
			int updateCount = pstmt3.executeUpdate();
			System.out.println("회원이 삭제 되었습니다. (" + updateCount + ")");
			pstmt3.close();
		} catch (Exception e)
		{
			System.out.println("회원이 삭제되지 않았습니다.\n" + e.getMessage());
		}
	}

	public static void addBook(Connection con)
	{
		PreparedStatement pstmt4;
		System.out.println("도서명 :  ");
		String bName = sc.nextLine();
		System.out.println("분류번호 : ");
		String bNum = sc.nextLine();

		System.out.println("출판사명: ");
		String company = sc.nextLine();
		System.out.println("저 자: ");
		String author = sc.nextLine();

		try
		{
			String bInsert = "insert into bookDB values(?, ?, ?, ?, 1)";
			pstmt4 = con.prepareStatement(bInsert);
			pstmt4.setString(1, bName);
			pstmt4.setString(2, bNum);
			pstmt4.setString(3, company);
			pstmt4.setString(4, author);
			int updateCount = pstmt4.executeUpdate();
			System.out.println("책이 추가 되었습니다. (" + updateCount + ")");
			pstmt4.close();
		} catch (Exception e)
		{
			System.out.println("책 추가 중 오류가 발생했습니다.\n" + e.getMessage());
		}
	}

	public static void selBook(Connection con)
	{
		PreparedStatement pstmt5;
		System.out.print("조회할 도서명 : ");
		String search = sc.nextLine();

		try
		{
			String bSelect = "select * from bookDB where bName = ?";
			pstmt5 = con.prepareStatement(bSelect);
			pstmt5.setString(1, search);
			ResultSet rs = pstmt5.executeQuery();
			int count = 0;
			while (rs.next())
			{
				count++;
				String bName = rs.getString("bName");
				String bNum = rs.getString("bNum");
				String quantity = rs.getString("quantity");
				String company = rs.getString("company");
				String author = rs.getString("author");
				System.out.println("도서명 : " + bName);
				System.out.println("분류번호 : " + bNum);
				System.out.println("출판사명 : " + company);
				System.out.println("저 자 : " + author);
				System.out.println("권 수 : " + quantity);
				System.out.println("----------------");
			}
			if (count == 0)
				System.out.println("조회할 데이터가 없습니다.");
			pstmt5.close();
		} catch (Exception e)
		{
			System.out.println("조회 중 오류가 발생했습니다.\n" + e.getMessage());

		}
	}

	public static void delBook(Connection con)
	{
		PreparedStatement pstmt6;
		System.out.print("도서명 : ");
		String bName = sc.nextLine();
		System.out.print("분류번호 : ");
		String bNum = sc.nextLine();

		try
		{
			String bDelete = "delete from bookDB where bName = ? and bNum = ?";
			pstmt6 = con.prepareStatement(bDelete);
			pstmt6.setString(1, bName);
			pstmt6.setString(2, bNum);
			int updateCount = pstmt6.executeUpdate();
			System.out.println("도서가 삭제 되었습니다. (" + updateCount + ")");
			pstmt6.close();
		} catch (Exception e)
		{
			System.out.println("도서가 삭제되지 않았습니다.\n" + e.getMessage());
		}
	}

	public static void bookList(Connection con)
	{
		PreparedStatement pstmt7;
		System.out.print("전체 도서를 조회합니다.");
		try
		{
			String bList = "select * from bookDB";
			pstmt7 = con.prepareStatement(bList);
			ResultSet rs = pstmt7.executeQuery();
			int count = 0;
			while (rs.next())
			{
				count++;
				String bName = rs.getString("bName");
				String bNum = rs.getString("bNum");
				String quantity = rs.getString("quantity");
				String company = rs.getString("company");
				String author = rs.getString("author");
				System.out.println("도서명 : " + bName);
				System.out.println("분류번호 : " + bNum);
				System.out.println("권 수 : " + quantity);
				System.out.println("출판사명 : " + company);
				System.out.println("저 자 : " + author);
				System.out.println("---------------------");
			}
			if (count == 0)
				System.out.println("조회할 수 있는 도서가 없습니다.");
			pstmt7.close();
		} catch (Exception e)
		{
			System.out.println("조회 중 오류가 발생했습니다.\n" + e.getMessage());
		}

	}

	public static void rentBook(Connection con)
	{

		PreparedStatement pstmt8;
		PreparedStatement pstmt12;
		PreparedStatement pstmt14;
		PreparedStatement pstmt19;
		PreparedStatement pstmt22;
		PreparedStatement pstmt23;
		
		System.out.println("회원 ID: ");
		String id = sc.nextLine();
		System.out.println("대출할 책 이름: ");
		String bname = sc.nextLine();
		try
		{
			String bSelect = "select * from memberdb a, bookdb b where a.id = ? and b.bname= ?";
			pstmt8 = con.prepareStatement(bSelect);
			pstmt8.setString(1, id);
			pstmt8.setString(2, bname);
			ResultSet rs = pstmt8.executeQuery();
			int count = 0;
			while (rs.next())
			{
				count++;
			}
			if (count == 0)
			{
				System.out.println("해당하는 자료가 없습니다.");
				return;
			}

			String mblack = "select * from memberdb where id = ? and warning = 3";
			pstmt19 = con.prepareStatement(mblack);
			pstmt19.setString(1, id);
			rs = pstmt19.executeQuery();
			int count1 = 0;
			while (rs.next())
			{
				count1++;
				System.out.println("해당 회원은 블랙회원입니다.");
				return;
			}
			if (count1 == 0)
			{

				System.out.println("분류번호 :  ");
				String bNum = sc.nextLine();
				
				String mTotal = "select * from memberdb where id = ? and total<3";
				pstmt22 = con.prepareStatement(mTotal);
				pstmt22.setString(1, id);
				ResultSet rs2 = pstmt22.executeQuery();
				int count2 =0;
				while(rs2.next())
				{
					count2++;
					
				}
				if (count2==0)
				{
					System.out.println("책 3권을 다 빌렸습니다.");
					return;
				}
				pstmt22.close();
				

				String mTotal2 = "update memberdb set total=total+1 where id = ?";
				pstmt22 = con.prepareStatement(mTotal2);
				pstmt22.setString(1, id);
				
				pstmt22.executeUpdate();
				
				String bRent = "insert into rentDB values(?,?,?,sysdate,sysdate+7,null,0)";
				pstmt12 = con.prepareStatement(bRent);
				pstmt12.setString(1, bname);
				pstmt12.setString(2, bNum);
				pstmt12.setString(3, id);

				String qSelect = "select * from bookdb where bName = ? ";
				pstmt14 = con.prepareStatement(qSelect);
				pstmt14.setString(1, bname);
				rs = pstmt14.executeQuery();
				int num2 = 0;
				while (rs.next())
				{
					num2 = rs.getInt("quantity");
				}

				if (num2 == 0)
				{
					System.out.println("해당하는 책이 없습니다.");
					return;
				}
				String qRent = "update bookDB set quantity = quantity-1 where bNum = ?";
				pstmt14 = con.prepareStatement(qRent);
				pstmt14.setString(1, bNum);
				pstmt14.executeUpdate();

				int updateCount = pstmt12.executeUpdate();

				System.out.println("책이 대출 되었습니다. (" + updateCount + ")");
				pstmt12.close();

				pstmt8.close();
				pstmt12.close();
				pstmt14.close();
			}
		} catch (Exception e)
		{
			System.out.println("조회 중 오류가 발생했습니다.\n" + e.getMessage());
		}
	}

	public static void returnBook(Connection con)
	{

		PreparedStatement pstmt9;
		PreparedStatement pstmt13;
		PreparedStatement pstmt16;
		PreparedStatement pstmt24;

		System.out.println("ID: ");
		String ID = sc.nextLine();
		System.out.println("분류번호 :  ");
		String bNum = sc.nextLine();
		try
		{
			String dReturn = "select (round(sysdate-(select rentdate from rentdb a, memberdb b where a.id=b.id and a.id = ? and a.bNum = ? and  a.returndate is null),0)) - ((select extendnum from rentdb a, memberdb b where a.id=b.id and a.id = ? and a.bNum = ? and a.returndate is null)*7) as \"a\" from dual";
			pstmt16 = con.prepareStatement(dReturn);
			pstmt16.setString(1, ID);
			pstmt16.setString(2, bNum);
			pstmt16.setString(3, ID);
			pstmt16.setString(4, bNum);
			ResultSet rs = pstmt16.executeQuery();
			int count = 0;
			while (rs.next())
			{
				count = rs.getInt("a");
			}

			String bReturn = "update rentDB set returnDate = sysdate where ID = ? and bNum = ? and returndate is null";
			pstmt9 = con.prepareStatement(bReturn);
			pstmt9.setString(1, ID);
			pstmt9.setString(2, bNum);

			int updateCount = pstmt9.executeUpdate();
			System.out.println("책이 반납 되었습니다. (" + updateCount + ")");
			pstmt9.close();

			String qSelect = "select * from bookdb where bNum = ?  ";
			pstmt13 = con.prepareStatement(qSelect);
			pstmt13.setString(1, bNum);
			rs = pstmt13.executeQuery();
			int num2 = 0;
			while (rs.next())
			{
				num2 = rs.getInt("quantity");
			}

			String qRent = "update bookDB set quantity = quantity+1 where bNum = ?";
			pstmt13 = con.prepareStatement(qRent);
			pstmt13.setString(1, bNum);
			pstmt13.executeUpdate();
			
			String rTotal="update memberdb set total=total-1 where id = ?";
			pstmt24 = con.prepareStatement(rTotal);
			pstmt24.setString(1, ID);
			
			pstmt24.executeUpdate();

		} catch (Exception e)
		{
			System.out.println("반납 중 오류가 발생했습니다.\n" + e.getMessage());
		}
	}

	public static void extendBook(Connection con)
	{

		PreparedStatement pstmt10;
		PreparedStatement pstmt11;
		PreparedStatement pstmt21;

		System.out.println("ID: ");
		String ID = sc.nextLine();
		System.out.println("분류번호 :  ");
		String bNum = sc.nextLine();

		try
		{
			String bReturn = "select * from rentDB where id = ? and bNum = ? and returndate is not null ";
			pstmt21 = con.prepareStatement(bReturn);
			pstmt21.setString(1, ID);
			pstmt21.setString(2, bNum);
			ResultSet rs1 = pstmt21.executeQuery();
			int count1 = 0;
			while (rs1.next())
			{
				count1++;
				System.out.println("이미 반납된 책입니다.");
			}
			if (count1 == 0)
			{
				String eNum = "select * from rentDB where bnum = ?";
				pstmt10 = con.prepareStatement(eNum);
				pstmt10.setString(1, bNum);
				ResultSet rs = pstmt10.executeQuery();
				int count = 0;
				int num = 0;
				while (rs.next())
				{
					count++;
					num = rs.getInt("extendNum");
					if (num == 0)
					{
						String bExtend = "update rentDB set expectDate = expectDate+7,extendNum = 1 where id= ? and bNum = ?";
						pstmt11 = con.prepareStatement(bExtend);
						pstmt11.setString(1, ID);
						pstmt11.setString(2, bNum);

						pstmt11.executeUpdate();
						System.out.println("기한이 연장 되었습니다.");
					} else
						{System.out.println("기한을 연장할 수 없습니다.");}
					break;
				}

				if (count == 0)
					System.out.println("조회할 데이터가 없습니다.");
				pstmt10.close();
				pstmt21.close();
			}
		} catch (Exception e)
		{
			System.out.println("조회 중 오류가 발생했습니다.\n" + e.getMessage());
		}
	}

	public static void getWarning(Connection con)
	{
		PreparedStatement pstmt15;
		PreparedStatement pstmt18;

		System.out.println("회원 ID : ");
		String ID = sc.nextLine();

		try
		{
			String wInsert = "select * from memberDB where Id =?";
			pstmt15 = con.prepareStatement(wInsert);
			pstmt15.setString(1, ID);
			ResultSet rs = pstmt15.executeQuery();

			String warn = "update memberdb set warning = warning+1 where id = ? ";
			pstmt18 = con.prepareStatement(warn);
			pstmt18.setString(1, ID);
			pstmt18.executeUpdate();

			System.out.println(ID + "회원에게 경고가 주어졌습니다");
			pstmt15.close();
			pstmt18.close();

		} catch (Exception e)
		{
			System.out.println("경고 중 오류가 발생했습니다.\n" + e.getMessage());

		}
	}

	public static void blackList(Connection con)
	{
		PreparedStatement pstmt20;

		try
		{
			String black = "select * from memberDB where warning >= 3";
			pstmt20 = con.prepareStatement(black);
			ResultSet rs = pstmt20.executeQuery();

			int count = 0;
			while (rs.next())
			{
				count++;
				System.out.println("id : " + rs.getString("id"));
				System.out.println("phonenumber : " + rs.getString("phonenumber"));
				System.out.println("--------------------------");

			}
			if (count == 0)
				System.out.println("블랙회원이 없습니다.");
			pstmt20.close();
		} catch (Exception e)
		{
			System.out.println("조회 중 오류가 발생하였습니다. \n" + e.getMessage());
		}
	}

	public static void main(String[] args)
	{
		Connection con;

		try
		{
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger");

			while (true)
			{

				showMenu1();
				System.out.println("숫자를 입력하세요");
				int choice = sc.nextInt();
				sc.nextLine();

				if (choice == 0)
				{
					System.out.println("프로그램을 종료합니다.");

					con.close();
					return;
				}

				else if (choice > 4)
				{
					System.out.println("잘못입력하셨습니다.");
					break;
				}

				else if (choice == 1)
				{
					showMenu2();
					int choice2 = sc.nextInt();
					sc.nextLine();

					while (true)
					{
						if (choice2 == 1)
						{
							System.out.println("신규회원 등록");
							addMember(con);
							break;
						} else if (choice2 == 2)
						{
							System.out.println("회원정보 조회");
							selMember(con);
							break;
						}

						else if (choice2 == 3)
						{
							System.out.println("회원 탈퇴하기");
							delMember(con);
							break;
						} else if (choice2 == 4)
						{
							System.out.println("회원 경고");
							getWarning(con);
							break;
						} else if (choice2 == 5)
						{
							System.out.println("블랙리스트 조회");
							blackList(con);
							break;
						}

						else if (choice2 == 6)
						{
							System.out.println("이전 메뉴로");
						}
						break;
					}
				} else if (choice == 2)
				{
					showMenu3();
					int choice3 = sc.nextInt();
					sc.nextLine();

					while (true)
					{
						if (choice3 == 1)
						{
							System.out.println("도서 대출");
							rentBook(con);
							break;

						} else if (choice3 == 2)
						{
							System.out.println("도서 반납");
							returnBook(con);
							break;
						} else if (choice3 == 3)
						{
							System.out.println("대출기한연장");
							extendBook(con);
							break;
						} else if (choice3 == 4)
						{
							System.out.println("이전 메뉴로");
						}
						break;
					}
				} else if (choice == 3)
				{
					showMenu4();
					int choice4 = sc.nextInt();
					sc.nextLine();

					while (true)
					{
						if (choice4 == 1)
						{
							System.out.println("도서 등록");
							addBook(con);
							break;

						} else if (choice4 == 2)
						{
							System.out.println("도서 검색");
							selBook(con);
							break;
						} else if (choice4 == 3)
						{
							System.out.println("도서 삭제");
							delBook(con);
							break;
						} else if (choice4 == 4)
						{
							System.out.println("전체 도서목록");
							bookList(con);
						} else if (choice4 == 5)
						{
							System.out.println("이전 메뉴로");
						}
						break;
					}
				}
			}
		} catch (SQLException sqle)
		{
			System.out.println("Connection Error");
			sqle.printStackTrace();
		}
	}
}