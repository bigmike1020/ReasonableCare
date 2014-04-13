package reasonablecare;

import java.util.Calendar;

public class CreditCardSystem {

	public CreditCardSystem()
	{
		
	}
	
	/**
	 * Contacts the credit card company to get preapproval for a copay charge
	 * 
	 * @param credit card number
	 * @return true if preapproved false if declined
	 */
	public boolean getPreapproval(String creditCardNumber)
	{
		return true;
	}
	
	/**
	 * 
	 */
	public boolean validateCreditCard(String creditCardNumber, int ccMonth, int ccYear)
	{
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);

		if (ccYear>year)
			{
			//if (ccMonth >= month)
				return true;
			}
		else if(ccYear==year)
		{
			if (ccMonth >= month)
				return true;
			}
			return false;
	}
	
	
	
}
