export class LandingPage {
  navigateTo(url: string) {
    return cy.visit(url);
  }

  checkPageTitle(title: string) {
    return cy.contains(title);
  }
}
