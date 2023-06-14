FROM --platform=$BUILDPLATFORM node:lts AS development

WORKDIR /duykhanhhotel/frontend
COPY package.json /duykhanhhotel/frontend/package.json
COPY package-lock.json /duykhanhhotel/frontend/package-lock.json

RUN npm ci
COPY . /duykhanhhotel/frontend

ENV CI=true
ENV PORT=3000

CMD [ "npm", "start" ]

FROM development AS build

RUN ["npm", "run", "build"]

FROM nginx

COPY --from=build /duykhanhhotel/frontend/build /usr/share/nginx/html