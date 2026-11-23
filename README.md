# Android-Capstone

## Installation Instructions
Download capstone-version to your local android device from the github releases page.
Locate the file and run it with package manager, or preferred manager of choice.
If prompted with a security message, allow your browser to install unknown apps.
If prompted, allow Google Play Protect to scan the app.
Once completed, Press Install.
Press open or tap the application icon to launch the app.

## Demo Customer Configuration
The application is automatically created to configure the customer as Brandy Chandra. No user action is required.

## Base URL configuration.
The application is automatically configured to use the Adventureworks API as its base URL. No user action is required.

## Known limitations
This application will not allow you to change users, add or update information in the Adventureworks API, or create actual orders.

## Automated Tests Summary
We added automated tests to check that the main parts of our app are working correctly.

### Unit Tests
- **Order History:** Checked that orders load, sort correctly, and handle errors.
- **Order Details:** Tested order statuses, unknown statuses, and subtotal calculations.
- **Order Data:** Tested how orders are mapped and retrieved from the repository.
- **Home Screen:** Tested loading recent products and orders, along with refreshing product data.

### Instrumented Tests
- **Product Database:** Tested adding, updating, replacing, and retrieving products.
- **Recently Viewed Products:** Checked that products are sorted by most recent, only the last 10 are kept, and history can be cleared.
- **Product Details:** Tested saving, updating, and retrieving product details from the database.

### Test Results
To run the automated tests, open a terminal in the project's folder and use the commands below. 
For instrumented tests, make sure an Android device with USB debugging enabled 
is connected or an emulator is running. Both automated test runs completed successfully.

**Unit Tests:**
`bash ./gradlew testDebugUnitTest`

**Instrumented Tests:**
`bash ./gradlew connectedDebugAndroidTest --no-configuration-cache`

The instrumented tests were run on a Motorola phone.

### Testing Limitations

Not every feature has automated tests. We also manually tested order navigation, 
screen rotation, error messages, and the Retry button when the connection fails.

Some Home Screen UI tests are commented out and were not included in the test runs.

## Final Product Backlog
<img width="1628" height="888" alt="image" src="https://github.com/user-attachments/assets/ba50bd4f-3b99-40bd-826e-9c85aa845372" />

## Scrum Artifacts
https://docs.google.com/document/d/1sT2_gvkrLmQqhybrYrVy-nCTq41GXemaXPuLua8WPxw/edit?usp=sharing