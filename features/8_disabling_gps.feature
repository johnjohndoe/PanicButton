@gps_settings
Feature: Sending sms with different gps status

  Scenario: Setting up
    Given I wait upto 60 seconds for the "WizardActivity" screen to appear
    And I press "Set-Up"
    And I press "Get started"
    And I wait for 1 second
    And I enter "123456" into contact field 0
    And I press "Next"
    And I wait for 1 second
    And I press "Next"
    And I wait for 1 second
    And I enter "1234" into input field number 1
    And I press "Next"
    And I press "Setup Alarm"
    And I press "Learn"
    Then I see "Try Now! Repeatedly press the power button fast until you feel a vibration."
    And I press power button 5 times
    And I unlock device
    Then I see "Well done!"
    And I press "Next"
    And I press "Learn"
    And I click 5 times fast on calculation
    Then I see "Well done!"
    And I press "Next"
    And I press "Setup Disguise"
    And I press "Learn"
    And I press "Calculate!"
    Then I see "Try now! Hold down any button on the calculator."
    And I long press custom
    Then I see "Well done! Now enter your pincode to access settings."
    And I enter "1234" into input field number 1
    And I press "go"
    And I press "Finish"
    Then I see "Ready"

  Scenario: Start alarm with hardware button
    Given I wait for 5 seconds
    And I press power button 5 times
    And I unlock device
    And I start application
    And I long press custom
    And I enter "1234" into input field number 1
    And I press "go"
    Then I see "Alerting"
  #TODO: check sms text




  #TODO: add test on  starting alarm by : #    And I click 5 times fast on calculation

#  Scenario: Change GPS status
#    Then I switch gps



#  Scenario: Change GPS status
#    Then I switch gps
