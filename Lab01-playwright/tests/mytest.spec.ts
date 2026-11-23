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

// Тест 2: Амжилтгүй нэвтрэх
test('Буруу нууц үгээр нэвтрэх', async ({ page }) => {
  await page.goto('https://www.saucedemo.com/');

  // Зөв хэрэглэгчийн нэр, буруу нууц үг оруулах
  await page.getByPlaceholder('Username').fill('standard_user');
  await page.getByPlaceholder('Password').fill('wrong_sauce');

  await page.getByRole('button', { name: 'Login' }).click();

  // data-test атрибутаар алдааны мэдээлэл олох
  const errorMessage = page.locator('[data-test="error"]');

  // Алдааны мэдээлэл харагдаж буй эсэхийг шалгах
  await expect(errorMessage).toBeVisible();
  await expect(errorMessage).toContainText(
      'Username and password do not match'
  );

  // Нэвтрэх хуудас дээр хэвээр байгааг шалгах
  await expect(page).toHaveURL('https://www.saucedemo.com/');
});