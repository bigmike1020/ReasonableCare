package reasonablecare;


public class InsuranceCompanySystem {
	
	//default constructor
	public InsuranceCompanySystem()
	{
		
	}
	
	public boolean getDeductiblePaid(String insuranceProvider, String policyNumber)
	{
		/*
		 * Assume deductible has not been paid.
		 * 
		 * If there is time, possibly implement that if there have been 5+ appointments
		 * in a given year that the deductible has been paid
		 */
		return false;
	}
	
	
	public int getCopay(String apptType, int doctorID, String insCompany, String insNumber)
	{
		return 20;
	}

}
