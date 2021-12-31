module.exports = {
  moduleNameMapper: {
    '@core/(.*)': '<rootDir>/src/app/core/$1',
    '@shared/(.*)': '<rootDir>/src/app/shared/$1',
    '@modules/(.*)': '<rootDir>/src/app/modules/$1',
    '@env/(.*)': '<rootDir>/src/environments/environment',
  },
  preset: 'jest-preset-angular',
  roots: ['<rootDir>/src/'],
  collectCoverage: true,
  coverageReporters: ['html'],
  coverageDirectory: 'coverage',
  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
  testMatch: ["<rootDir>/src/**/*.spec.ts"]
};
