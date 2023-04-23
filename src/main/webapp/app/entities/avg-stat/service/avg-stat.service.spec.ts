import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAvgStat } from '../avg-stat.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../avg-stat.test-samples';

import { AvgStatService } from './avg-stat.service';

const requireRestSample: IAvgStat = {
  ...sampleWithRequiredData,
};

describe('AvgStat Service', () => {
  let service: AvgStatService;
  let httpMock: HttpTestingController;
  let expectedResult: IAvgStat | IAvgStat[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AvgStatService);
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

    it('should create a AvgStat', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const avgStat = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(avgStat).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AvgStat', () => {
      const avgStat = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(avgStat).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AvgStat', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AvgStat', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AvgStat', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAvgStatToCollectionIfMissing', () => {
      it('should add a AvgStat to an empty array', () => {
        const avgStat: IAvgStat = sampleWithRequiredData;
        expectedResult = service.addAvgStatToCollectionIfMissing([], avgStat);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(avgStat);
      });

      it('should not add a AvgStat to an array that contains it', () => {
        const avgStat: IAvgStat = sampleWithRequiredData;
        const avgStatCollection: IAvgStat[] = [
          {
            ...avgStat,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAvgStatToCollectionIfMissing(avgStatCollection, avgStat);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AvgStat to an array that doesn't contain it", () => {
        const avgStat: IAvgStat = sampleWithRequiredData;
        const avgStatCollection: IAvgStat[] = [sampleWithPartialData];
        expectedResult = service.addAvgStatToCollectionIfMissing(avgStatCollection, avgStat);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(avgStat);
      });

      it('should add only unique AvgStat to an array', () => {
        const avgStatArray: IAvgStat[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const avgStatCollection: IAvgStat[] = [sampleWithRequiredData];
        expectedResult = service.addAvgStatToCollectionIfMissing(avgStatCollection, ...avgStatArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const avgStat: IAvgStat = sampleWithRequiredData;
        const avgStat2: IAvgStat = sampleWithPartialData;
        expectedResult = service.addAvgStatToCollectionIfMissing([], avgStat, avgStat2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(avgStat);
        expect(expectedResult).toContain(avgStat2);
      });

      it('should accept null and undefined values', () => {
        const avgStat: IAvgStat = sampleWithRequiredData;
        expectedResult = service.addAvgStatToCollectionIfMissing([], null, avgStat, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(avgStat);
      });

      it('should return initial array if no AvgStat is added', () => {
        const avgStatCollection: IAvgStat[] = [sampleWithRequiredData];
        expectedResult = service.addAvgStatToCollectionIfMissing(avgStatCollection, undefined, null);
        expect(expectedResult).toEqual(avgStatCollection);
      });
    });

    describe('compareAvgStat', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAvgStat(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAvgStat(entity1, entity2);
        const compareResult2 = service.compareAvgStat(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAvgStat(entity1, entity2);
        const compareResult2 = service.compareAvgStat(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAvgStat(entity1, entity2);
        const compareResult2 = service.compareAvgStat(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
