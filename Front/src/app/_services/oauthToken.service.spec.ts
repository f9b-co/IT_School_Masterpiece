import { TestBed } from '@angular/core/testing';

import { OauthTokenService } from './oauthToken.service';

describe('OauthTokenService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OauthTokenService = TestBed.get(OauthTokenService);
    expect(service).toBeTruthy();
  });
});
