import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { LogWork } from './log-work.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<LogWork>;

@Injectable()
export class LogWorkService {

    private resourceUrl =  SERVER_API_URL + 'api/log-works';
    private resourceUrlLog =  SERVER_API_URL + 'api/log-works-by-project';

    constructor(private http: HttpClient) { }

    create(logWork: LogWork): Observable<EntityResponseType> {
        const copy = this.convert(logWork);
        return this.http.post<LogWork>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(logWork: LogWork): Observable<EntityResponseType> {
        const copy = this.convert(logWork);
        return this.http.put<LogWork>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LogWork>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LogWork[]>> {
        const options = createRequestOption(req);
        return this.http.get<LogWork[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LogWork[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    getByProject(id: any): Observable<HttpResponse<LogWork[]>> {
        return this.http.get<LogWork[]>(`${this.resourceUrlLog}/${id}`, { observe: 'response' })
            .map((res: HttpResponse<LogWork[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LogWork = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LogWork[]>): HttpResponse<LogWork[]> {
        const jsonResponse: LogWork[] = res.body;
        const body: LogWork[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LogWork.
     */
    private convertItemFromServer(logWork: LogWork): LogWork {
        const copy: LogWork = Object.assign({}, logWork);
        return copy;
    }

    /**
     * Convert a LogWork to a JSON which can be sent to the server.
     */
    private convert(logWork: LogWork): LogWork {
        const copy: LogWork = Object.assign({}, logWork);
        return copy;
    }
}
