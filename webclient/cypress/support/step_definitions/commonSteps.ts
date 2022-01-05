import { LandingPage } from '../page-objects/LandingPage.po';
import { Then, Given, After } from 'cypress-cucumber-preprocessor/steps';

let landingPage: LandingPage;

beforeEach(() => {
  landingPage = new LandingPage();
});

Given('Enter application url {string}', (url) => {
  landingPage.navigateTo(url);
});

Then('Show title {string}', (title) => {
  landingPage.checkPageTitle(title);
});

After(() => {
  cy.clearCookies();
});
