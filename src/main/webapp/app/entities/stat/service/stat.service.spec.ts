import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStat } from '../stat.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../stat.test-samples';

import { StatService } from './stat.service';

const requireRestSample: IStat = {
  ...sampleWithRequiredData,
};

describe('Stat Service', () => {
  let service: StatService;
  let httpMock: HttpTestingController;
  let expectedResult: IStat | IStat[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(StatService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Stat', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const stat = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(stat).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Stat', () => {
      const stat = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(stat).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Stat', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Stat', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Stat', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStatToCollectionIfMissing', () => {
      it('should add a Stat to an empty array', () => {
        const stat: IStat = sampleWithRequiredData;
        expectedResult = service.addStatToCollectionIfMissing([], stat);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stat);
      });

      it('should not add a Stat to an array that contains it', () => {
        const stat: IStat = sampleWithRequiredData;
        const statCollection: IStat[] = [
          {
            ...stat,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStatToCollectionIfMissing(statCollection, stat);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Stat to an array that doesn't contain it", () => {
        const stat: IStat = sampleWithRequiredData;
        const statCollection: IStat[] = [sampleWithPartialData];
        expectedResult = service.addStatToCollectionIfMissing(statCollection, stat);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stat);
      });

      it('should add only unique Stat to an array', () => {
        const statArray: IStat[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const statCollection: IStat[] = [sampleWithRequiredData];
        expectedResult = service.addStatToCollectionIfMissing(statCollection, ...statArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const stat: IStat = sampleWithRequiredData;
        const stat2: IStat = sampleWithPartialData;
        expectedResult = service.addStatToCollectionIfMissing([], stat, stat2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(stat);
        expect(expectedResult).toContain(stat2);
      });

      it('should accept null and undefined values', () => {
        const stat: IStat = sampleWithRequiredData;
        expectedResult = service.addStatToCollectionIfMissing([], null, stat, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(stat);
      });

      it('should return initial array if no Stat is added', () => {
        const statCollection: IStat[] = [sampleWithRequiredData];
        expectedResult = service.addStatToCollectionIfMissing(statCollection, undefined, null);
        expect(expectedResult).toEqual(statCollection);
      });
    });

    describe('compareStat', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStat(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStat(entity1, entity2);
        const compareResult2 = service.compareStat(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareStat(entity1, entity2);
        const compareResult2 = service.compareStat(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareStat(entity1, entity2);
        const compareResult2 = service.compareStat(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
