import { test, expect, type Page } from '@playwright/test';

// SauceDemo системд нэвтрэх функц
async function login(page: Page) {
  await page.goto('https://www.saucedemo.com/');

  // Хэрэглэгчийн нэр болон нууц үг оруулах
  await page.getByPlaceholder('Username').fill('standard_user');
  await page.getByPlaceholder('Password').fill('secret_sauce');

  // Нэвтрэх товч дарах
  await page.getByRole('button', { name: 'Login' }).click();
}

// Системээс гарах функц
async function logout(page: Page) {
  // Цэс нээх
  await page.getByRole('button', { name: 'Open Menu' }).click();

  // Logout хийх
  await page.locator('[data-test="logout-sidebar-link"]').click();
}

// Тест 1: Амжилттай нэвтрэх
test('Амжилттай нэвтрэх', async ({ page }) => {
  await login(page);

  // Бүтээгдэхүүний хуудас нээгдсэн эсэхийг шалгах
  await expect(page).toHaveURL(/inventory.html/);
  await expect(page.getByText('Products')).toBeVisible();

  await logout(page);

  // Системээс гарсныг шалгах
  await expect(page).toHaveURL('https://www.saucedemo.com/');
});