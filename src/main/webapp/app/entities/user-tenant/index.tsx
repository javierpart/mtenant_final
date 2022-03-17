import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserTenant from './user-tenant';
import UserTenantDetail from './user-tenant-detail';
import UserTenantUpdate from './user-tenant-update';
import UserTenantDeleteDialog from './user-tenant-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserTenantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserTenantUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserTenantDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserTenant} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserTenantDeleteDialog} />
  </>
);

export default Routes;
