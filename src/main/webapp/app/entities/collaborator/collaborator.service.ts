import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Collaborator } from './collaborator.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Collaborator>;

@Injectable()
export class CollaboratorService {

    private resourceUrl =  SERVER_API_URL + 'api/collaborators';

    constructor(private http: HttpClient) { }

    create(collaborator: Collaborator): Observable<EntityResponseType> {
        const copy = this.convert(collaborator);
        return this.http.post<Collaborator>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(collaborator: Collaborator): Observable<EntityResponseType> {
        const copy = this.convert(collaborator);
        return this.http.put<Collaborator>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Collaborator>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Collaborator[]>> {
        const options = createRequestOption(req);
        return this.http.get<Collaborator[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Collaborator[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Collaborator = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Collaborator[]>): HttpResponse<Collaborator[]> {
        const jsonResponse: Collaborator[] = res.body;
        const body: Collaborator[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Collaborator.
     */
    private convertItemFromServer(collaborator: Collaborator): Collaborator {
        const copy: Collaborator = Object.assign({}, collaborator);
        return copy;
    }

    /**
     * Convert a Collaborator to a JSON which can be sent to the server.
     */
    private convert(collaborator: Collaborator): Collaborator {
        const copy: Collaborator = Object.assign({}, collaborator);
        return copy;
    }
}
