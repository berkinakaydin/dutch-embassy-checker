public class Story {
  public Story() {
    Scenarios sc = new Scenarios();
    System.out.println("Navigate To Website");
    sc.navigateTo("https://www.netherlandsworldwide.nl/countries/turkey/travel/applying-for-a-long-stay-visa-mvv");
    
    System.out.println("Click Make an appointment");
    sc.clickLink("Make an appointment");

    System.out.println("Click Schedule Appointment");
    sc.clickLink("Schedule Appointment");

    System.out.println("Pick Istanbul CG from plhMain_cboVAC");
    sc.pickItemByName("Istanbul CG", "plhMain_cboVAC");

    System.out.println("Click plhMain_btnSubmit");
    sc.clickButton("plhMain_btnSubmit");

    System.out.println("Pick Istanbul CG from plhMain_cboVisaCategory");
    sc.pickItemByValue("8", "plhMain_cboVisaCategory");

    System.out.println("Click plhMain_btnSubmit");
    sc.clickButton("plhMain_btnSubmit");

    System.out.println("Check availability");
    boolean isAvailable = sc.checkAvailability();
    if (isAvailable) {
      System.out.println("Fill Input");
      sc.fillInput();

      System.out.println("Click plhMain_btnSubmit");
      sc.clickButton("plhMain_btnSubmit");

      System.out.println("Check availability");
      sc.checkAvailability();

      System.out.println("Click >>");
      sc.clickLink(">>");

      System.out.println("Check availability");
      sc.checkAvailability();
    }
  }
}
