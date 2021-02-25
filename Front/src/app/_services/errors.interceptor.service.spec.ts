import { TestBed } from '@angular/core/testing';

import { ErrorsInterceptorService } from './errors.interceptor.service';

describe('InterceptorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ErrorsInterceptorService = TestBed.get(ErrorsInterceptorService);
    expect(service).toBeTruthy();
  });
});
