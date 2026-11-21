# Android-Capstone

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

Both automated test runs completed successfully.

**Unit Tests:**
`bash ./gradlew testDebugUnitTest`

**Instrumented Tests:**
`bash ./gradlew connectedDebugAndroidTest --no-configuration-cache`

The instrumented tests were run on a Motorola phone.

### Testing Limitations

Not every feature has automated tests. We also manually tested order navigation, screen rotation, error messages, and the Retry button when the connection fails.

Some Home Screen UI tests are commented out and were not included in the test runs.