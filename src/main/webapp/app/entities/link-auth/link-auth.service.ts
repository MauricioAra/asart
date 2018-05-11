import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { LinkAuth } from './link-auth.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<LinkAuth>;

@Injectable()
export class LinkAuthService {

    private resourceUrl =  SERVER_API_URL + 'api/link-auths';

    constructor(private http: HttpClient) { }

    create(linkAuth: LinkAuth): Observable<EntityResponseType> {
        const copy = this.convert(linkAuth);
        return this.http.post<LinkAuth>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(linkAuth: LinkAuth): Observable<EntityResponseType> {
        const copy = this.convert(linkAuth);
        return this.http.put<LinkAuth>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<LinkAuth>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<LinkAuth[]>> {
        const options = createRequestOption(req);
        return this.http.get<LinkAuth[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<LinkAuth[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: LinkAuth = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<LinkAuth[]>): HttpResponse<LinkAuth[]> {
        const jsonResponse: LinkAuth[] = res.body;
        const body: LinkAuth[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to LinkAuth.
     */
    private convertItemFromServer(linkAuth: LinkAuth): LinkAuth {
        const copy: LinkAuth = Object.assign({}, linkAuth);
        return copy;
    }

    /**
     * Convert a LinkAuth to a JSON which can be sent to the server.
     */
    private convert(linkAuth: LinkAuth): LinkAuth {
        const copy: LinkAuth = Object.assign({}, linkAuth);
        return copy;
    }
}
