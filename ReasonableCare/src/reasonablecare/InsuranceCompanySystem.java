package reasonablecare;


public class InsuranceCompanySystem {
	
	//TODO implement methods to return deductible and copayment information
	
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
		//TODO determine if doctor is specialist
		//TODO possible variance of copay based on Acme/non-Acme insurance
		//TODO modify copay amount for vaccinations and physicals
		
		return 20;
	}

}
